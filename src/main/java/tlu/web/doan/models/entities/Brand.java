package tlu.web.doan.models.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    private String country;

    @Column(name = "logo_url")
    private String logoUrl;
}