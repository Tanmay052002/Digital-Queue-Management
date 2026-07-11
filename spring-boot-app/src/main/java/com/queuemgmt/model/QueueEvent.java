package com.queuemgmt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// QUEUE_EVENT table from the ERD
@Entity
@Table(name = "queue_event")
public class QueueEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_id")
    private Long tokenId;

    @Column(name = "event_type")
    private String eventType; // e.g. TOKEN_GENERATED, TOKEN_SERVED

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public QueueEvent() {
    }

    public QueueEvent(Long tokenId, String eventType, LocalDateTime createdAt) {
        this.tokenId = tokenId;
        this.eventType = eventType;
        this.createdAt = createdAt;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
