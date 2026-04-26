package tlu.web.doan.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tlu.web.doan.dto.ProductCategoryDTO;
import tlu.web.doan.models.entities.ProductCategory;
import tlu.web.doan.repositories.ProductCategoryRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;

    public List<ProductCategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductCategoryDTO getCategoryById(Long id) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return convertToDTO(category);
    }

    public ProductCategoryDTO createCategory(ProductCategoryDTO dto, MultipartFile imageFile) throws IOException {
        if (dto.getSlug() != null && categoryRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new RuntimeException("Category with Slug " + dto.getSlug() + " already exists");
        }
        ProductCategory category = new ProductCategory();
        BeanUtils.copyProperties(dto, category, "id");
        if (dto.getParentId() != null) {
            ProductCategory parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parent);
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile, "PCMaster/ProductCategory_images");
            category.setImageUrl(imageUrl);
        }
        return convertToDTO(categoryRepository.save(category));
    }

    public ProductCategoryDTO updateCategory(Long id, ProductCategoryDTO dto, MultipartFile imageFile) throws IOException {
        ProductCategory existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existing.setName(dto.getName());
        if (dto.getSlug() != null && !dto.getSlug().equals(existing.getSlug()) && categoryRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new RuntimeException("Category with Slug " + dto.getSlug() + " already exists");
        }
        existing.setSlug(dto.getSlug());
        if (dto.getParentId() != null) {
            ProductCategory parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            existing.setParent(parent);
        } else {
            existing.setParent(null);
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile, "PCMaster/ProductCategory_images");
            existing.setImageUrl(imageUrl);
        }
        return convertToDTO(categoryRepository.save(existing));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private ProductCategoryDTO convertToDTO(ProductCategory category) {
        ProductCategoryDTO dto = new ProductCategoryDTO();
        BeanUtils.copyProperties(category, dto);
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }
        return dto;
    }
}