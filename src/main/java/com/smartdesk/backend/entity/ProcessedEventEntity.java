package com.smartdesk.backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "processed_event")
@Getter
@NoArgsConstructor
public class ProcessedEventEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private Instant processedAt;

    public ProcessedEventEntity(UUID id) {
        this.id = id;
        this.processedAt = Instant.now();
    }
}