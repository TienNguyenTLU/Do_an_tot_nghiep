package tlu.web.doan.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tlu.web.doan.dto.ProductCategoryDTO;
import tlu.web.doan.services.ProductCategoryService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<ProductCategoryDTO> createCategory(
            @RequestPart("category") ProductCategoryDTO categoryDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO, imageFile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> updateCategory(
            @PathVariable Long id,
            @RequestPart("category") ProductCategoryDTO categoryDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO, imageFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}