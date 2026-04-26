package tlu.web.doan.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tlu.web.doan.dto.NewsDTO;
import tlu.web.doan.models.entities.News;
import tlu.web.doan.models.entities.NewsCategory;
import tlu.web.doan.models.entities.User;
import tlu.web.doan.repositories.NewsRepository;
import tlu.web.doan.repositories.NewsCategoryRepository;
import tlu.web.doan.repositories.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NewsDTO getNewsById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));
        return convertToDTO(news);
    }

    public NewsDTO createNews(NewsDTO dto, MultipartFile thumbnail) throws IOException {
        if (dto.getSlug() != null && newsRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new RuntimeException("News with Slug " + dto.getSlug() + " already exists");
        }
        News news = new News();
        BeanUtils.copyProperties(dto, news, "id");
        
        if (dto.getAuthorId() != null) {
            User author = userRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            news.setAuthor(author);
        }
        
        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            Set<NewsCategory> categories = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));
            news.setCategories(categories);
        }

        if (thumbnail != null && !thumbnail.isEmpty()) {
            String thumbnailUrl = cloudinaryService.uploadFile(thumbnail, "PCMaster/News_images");
            news.setThumbnailUrl(thumbnailUrl);
        }
        
        news.setPublishedAt(dto.getPublishedAt() != null ? dto.getPublishedAt() : LocalDateTime.now());

        return convertToDTO(newsRepository.save(news));
    }

    public NewsDTO updateNews(Long id, NewsDTO dto, MultipartFile thumbnail) throws IOException {
        News existing = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));
                
        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());
        existing.setSummary(dto.getSummary());
        existing.setStatus(dto.getStatus());
        existing.setPublishedAt(dto.getPublishedAt());
        
        if (dto.getSlug() != null && !dto.getSlug().equals(existing.getSlug()) && newsRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new RuntimeException("News with Slug " + dto.getSlug() + " already exists");
        }
        existing.setSlug(dto.getSlug());

        if (dto.getAuthorId() != null) {
            User author = userRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            existing.setAuthor(author);
        } else {
            existing.setAuthor(null);
        }
        
        if (dto.getCategoryIds() != null) {
            Set<NewsCategory> categories = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));
            existing.setCategories(categories);
        } else {
            existing.setCategories(new HashSet<>());
        }

        if (thumbnail != null && !thumbnail.isEmpty()) {
            String thumbnailUrl = cloudinaryService.uploadFile(thumbnail, "PCMaster/News_images");
            existing.setThumbnailUrl(thumbnailUrl);
        }

        return convertToDTO(newsRepository.save(existing));
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    private NewsDTO convertToDTO(News news) {
        NewsDTO dto = new NewsDTO();
        BeanUtils.copyProperties(news, dto);
        if (news.getAuthor() != null) {
            dto.setAuthorId(news.getAuthor().getId());
        }
        if (news.getCategories() != null) {
            dto.setCategoryIds(news.getCategories().stream().map(NewsCategory::getId).collect(Collectors.toSet()));
        }
        return dto;
    }
}