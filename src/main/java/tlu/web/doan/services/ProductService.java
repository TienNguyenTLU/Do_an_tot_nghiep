package tlu.web.doan.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tlu.web.doan.dto.ProductDTO;
import tlu.web.doan.models.entities.Product;
import tlu.web.doan.models.entities.ProductCategory;
import tlu.web.doan.models.entities.Brand;
import tlu.web.doan.repositories.ProductRepository;
import tlu.web.doan.repositories.ProductCategoryRepository;
import tlu.web.doan.repositories.BrandRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final CloudinaryService cloudinaryService;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .filter(p -> p.getDeleted() == null || !p.getDeleted())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .filter(p -> p.getDeleted() == null || !p.getDeleted())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return convertToDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile imageFile) throws IOException {
        if (productDTO.getSku() != null && productRepository.findBySku(productDTO.getSku()).isPresent()) {
            throw new RuntimeException("Product with SKU " + productDTO.getSku() + " already exists");
        }
        if (productDTO.getSlug() != null && productRepository.findBySlug(productDTO.getSlug()).isPresent()) {
            throw new RuntimeException("Product with Slug " + productDTO.getSlug() + " already exists");
        }

        Product product = convertToEntity(productDTO);
        
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile);
            product.setMainImageUrl(imageUrl);
        }

        product.setCreatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile imageFile) throws IOException {
        Product existingProduct = productRepository.findById(id)
                .filter(p -> p.getDeleted() == null || !p.getDeleted())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // Update fields
        existingProduct.setName(productDTO.getName());
        existingProduct.setSellingPrice(productDTO.getSellingPrice());
        existingProduct.setCostPrice(productDTO.getCostPrice());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());
        existingProduct.setShortDescription(productDTO.getShortDescription());
        existingProduct.setDetailedDescription(productDTO.getDetailedDescription());
        existingProduct.setStatus(productDTO.getStatus());
        existingProduct.setSpecifications(productDTO.getSpecifications());
        existingProduct.setUpdatedAt(LocalDateTime.now());
        
        if (productDTO.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
            existingProduct.setCategory(category);
        } else {
            existingProduct.setCategory(null);
        }

        if (productDTO.getBrandId() != null) {
            Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
            existingProduct.setBrand(brand);
        } else {
            existingProduct.setBrand(null);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile);
            existingProduct.setMainImageUrl(imageUrl);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setDeleted(true);
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        if (product.getBrand() != null) {
            dto.setBrandId(product.getBrand().getId());
        }
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        
        if (dto.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        
        if (dto.getBrandId() != null) {
            Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);
        }

        return product;
    }
}