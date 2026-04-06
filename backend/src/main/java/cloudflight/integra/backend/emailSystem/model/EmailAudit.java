package cloudflight.integra.backend.emailSystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class EmailAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String recipientEmail;
    private String subject;
    private LocalDateTime sentAt;
    private String status;
    private String errorMessage;

    public EmailAudit() {
    }

    public EmailAudit(
        UUID id,
        String recipientEmail,
        String subject,
        LocalDateTime sentAt,
        String status,
        String errorMessage
    ) {
        this.id = id;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.sentAt = sentAt;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public UUID getId() {
        return id;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getSubject() {
        return subject;
    }


    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public EmailAudit setId(UUID id) {
        this.id = id;
        return this;
    }

    public EmailAudit setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
        return this;
    }

    public EmailAudit setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailAudit setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
        return this;
    }

    public EmailAudit setStatus(String status) {
        this.status = status;
        return this;
    }

    public EmailAudit setErrorMessage(String errorMessages) {
        this.errorMessage = errorMessages;
        return this;
    }
}
