package tlu.web.doan.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tlu.web.doan.dto.NewsDTO;
import tlu.web.doan.services.NewsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @PostMapping
    public ResponseEntity<NewsDTO> createNews(
            @RequestPart("news") NewsDTO newsDTO,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) throws IOException {
        return ResponseEntity.ok(newsService.createNews(newsDTO, thumbnail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(
            @PathVariable Long id,
            @RequestPart("news") NewsDTO newsDTO,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) throws IOException {
        return ResponseEntity.ok(newsService.updateNews(id, newsDTO, thumbnail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok().build();
    }
}