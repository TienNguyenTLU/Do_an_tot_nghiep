package tlu.web.doan.models.entities;

import jakarta.persistence.*;
import tlu.web.doan.models.enums.AppointmentStatus;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private User staff;

    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;

    private String location; // "STORE", "ONLINE"

    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}