package tlu.web.doan.models.entities;

import jakarta.persistence.*;
import tlu.web.doan.models.enums.DiscountType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discount_codes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", precision = 19, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "min_order_value", precision = 19, scale = 2)
    private BigDecimal minOrderValue;

    @Column(name = "issued_quantity")
    private Integer issuedQuantity;

    @Column(name = "used_count")
    private Integer usedCount = 0;

    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(name = "active")
    private Boolean active = true;
}