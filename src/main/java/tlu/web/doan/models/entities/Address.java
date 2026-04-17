package tlu.web.doan.models.entities;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String province;
    private String district;
    private String ward;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "is_default")
    private Boolean isDefault = false;
}