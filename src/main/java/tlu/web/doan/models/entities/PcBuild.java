package tlu.web.doan.models.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tlu.web.doan.models.enums.BuildStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "pc_builds")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PcBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "estimated_price", precision = 19, scale = 2)
    private BigDecimal estimatedPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BuildStatus status = BuildStatus.DRAFT;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", name = "components")
    private Map<String, Object> components; // e.g., {"cpu": {"id": 123, "quantity": 1, "snapshotPrice": ...}, ...}

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String notes;
}