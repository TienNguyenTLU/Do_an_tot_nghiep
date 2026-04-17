package tlu.web.doan.models.entities;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(name = "selling_price", precision = 19, scale = 2)
    private BigDecimal sellingPrice;

    @Column(name = "cost_price", precision = 19, scale = 2)
    private BigDecimal costPrice;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "main_image_url")
    private String mainImageUrl;

    @Column(name = "short_description", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "detailed_description", columnDefinition = "TEXT")
    private String detailedDescription;

    private String status; // "ACTIVE", "OUT_OF_STOCK", "DISCONTINUED"

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", name = "specifications")
    private Map<String, Object> specifications;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}