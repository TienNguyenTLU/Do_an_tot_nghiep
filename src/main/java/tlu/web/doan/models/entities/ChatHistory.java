package tlu.web.doan.models.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tlu.web.doan.models.enums.MessageSender;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "chat_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private User staff;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageSender sender;

    @Column(name = "sent_at")
    private LocalDateTime sentAt = LocalDateTime.now();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata; // Store RAG context, etc.
}