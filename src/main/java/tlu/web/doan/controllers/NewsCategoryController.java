package tlu.web.doan.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tlu.web.doan.dto.NewsCategoryDTO;
import tlu.web.doan.services.NewsCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/news-categories")
@RequiredArgsConstructor
public class NewsCategoryController {

    private final NewsCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<NewsCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<NewsCategoryDTO> createCategory(@RequestBody NewsCategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsCategoryDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody NewsCategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}