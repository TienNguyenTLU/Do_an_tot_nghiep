package tlu.web.doan.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // Can be null if product deleted

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "snapshot_price", precision = 19, scale = 2)
    private BigDecimal snapshotPrice;

    @Column(name = "snapshot_name")
    private String snapshotName;

    @Column(name = "snapshot_image")
    private String snapshotImage;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", name = "snapshot_specs")
    private Map<String, Object> snapshotSpecs;
}