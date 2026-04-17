package tlu.web.doan.models.entities;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "compatibility_rules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompatibilityRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "component_type_1", nullable = false)
    private String componentType1; // e.g., "cpu", "motherboard"

    @Column(name = "component_type_2", nullable = false)
    private String componentType2;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> conditions; // JSON describing compatibility logic

    private String description;
}