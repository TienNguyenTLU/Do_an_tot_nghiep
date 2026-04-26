package tlu.web.doan.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tlu.web.doan.dto.NewsCategoryDTO;
import tlu.web.doan.models.entities.NewsCategory;
import tlu.web.doan.repositories.NewsCategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsCategoryService {
    private final NewsCategoryRepository categoryRepository;

    public List<NewsCategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NewsCategoryDTO getCategoryById(Long id) {
        NewsCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return convertToDTO(category);
    }

    public NewsCategoryDTO createCategory(NewsCategoryDTO dto) {
        if (dto.getSlug() != null && categoryRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new RuntimeException("Category with Slug " + dto.getSlug() + " already exists");
        }
        NewsCategory category = new NewsCategory();
        BeanUtils.copyProperties(dto, category, "id");
        return convertToDTO(categoryRepository.save(category));
    }

    public NewsCategoryDTO updateCategory(Long id, NewsCategoryDTO dto) {
        NewsCategory existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existing.setName(dto.getName());
        if (dto.getSlug() != null && !dto.getSlug().equals(existing.getSlug()) && categoryRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new RuntimeException("Category with Slug " + dto.getSlug() + " already exists");
        }
        existing.setSlug(dto.getSlug());
        return convertToDTO(categoryRepository.save(existing));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private NewsCategoryDTO convertToDTO(NewsCategory category) {
        NewsCategoryDTO dto = new NewsCategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }
}