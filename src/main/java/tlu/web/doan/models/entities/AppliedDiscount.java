package tlu.web.doan.models.entities;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "applied_discounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_code_id", nullable = false)
    private DiscountCode discountCode;

    @Column(name = "discount_amount", precision = 19, scale = 2)
    private BigDecimal discountAmount;
}