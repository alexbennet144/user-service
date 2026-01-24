package com.smartdesk.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

import com.smartdesk.backend.constant.enums.HrEventStatus;
import com.smartdesk.backend.constant.enums.HrEventType;

@Entity
@Table(name = "hr_provisioning_event")
@Getter
@Setter
public class HrProvisioningEventEntity extends BaseEntity {

    private String externalEventId;
    private String employeeId;

    @Enumerated(EnumType.STRING)
    private HrEventType eventType;

    @Enumerated(EnumType.STRING)
    private HrEventStatus status;

    private Instant receivedAt;
    private Instant processedAt;
    private String errorMessage;
}