package tlu.web.doan.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Column(name = "display_order")
    private Integer displayOrder;
}
