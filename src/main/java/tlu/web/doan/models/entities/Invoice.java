package tlu.web.doan.models.entities;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", unique = true, nullable = false)
    private String invoiceNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt = LocalDateTime.now();

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(name = "subtotal", precision = 19, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "vat_amount", precision = 19, scale = 2)
    private BigDecimal vatAmount;

    @Column(name = "total_amount", precision = 19, scale = 2)
    private BigDecimal totalAmount;
}