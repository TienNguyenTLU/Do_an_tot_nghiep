package tlu.web.doan.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class NewsDTO {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String summary;
    private String thumbnailUrl;
    private Integer viewCount;
    private LocalDateTime publishedAt;
    private String status;
    private Long authorId;
    private Set<Long> categoryIds;
}