package com.queuemgmt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// NOTIFICATION table from the ERD.
// Records are created locally, but the actual "sending" is delegated
// to the separate .NET notification microservice (see NotificationClientService).
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_id")
    private Long tokenId;

    private String message;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    private String status; // SENT, FAILED

    public Notification() {
    }

    public Notification(Long tokenId, String message, LocalDateTime sentAt, String status) {
        this.tokenId = tokenId;
        this.message = message;
        this.sentAt = sentAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
