package tlu.web.doan.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "news")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String summary;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    private String status; // DRAFT, PUBLISHED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "news_categories",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<NewsCategory> categories = new HashSet<>();
}