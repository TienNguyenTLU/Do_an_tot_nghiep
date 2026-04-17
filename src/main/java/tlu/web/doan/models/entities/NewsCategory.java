package tlu.web.doan.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "news_categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;
}