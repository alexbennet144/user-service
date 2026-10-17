package com.yourorg.core.kafka.event;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HrUserCreatedEvent {

    private Metadata metadata;
    private Data data;

    @lombok.Data
    @NoArgsConstructor
    public static class Metadata {
        private UUID eventId;
        private LocalDateTime occurredAt;
        private String source;
    }

    @lombok.Data
    @NoArgsConstructor
    public static class Data {
        private UUID tenantId;
        private String employeeId;
        private String email;
        private String displayName;
    }
}
