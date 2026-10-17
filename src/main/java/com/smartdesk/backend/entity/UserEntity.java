package com.smartdesk.backend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.yourorg.core.constant.enums.UserStatus;
import com.yourorg.core.constant.enums.UserType;
import com.yourorg.core.kafka.event.HrUserCreatedPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "business_user")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "employee_id", nullable = false, unique = true)
    private String employeeId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "display_name")
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "terminated_at")
    private LocalDateTime terminatedAt;

    public static UserEntity from(HrUserCreatedPayload data, UUID tenantId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setTenantId(tenantId);
        userEntity.setEmployeeId(data.employeeId());
        userEntity.setEmail(data.email());
        userEntity.setDisplayName(data.displayName());
        userEntity.setStatus(data.status());
        userEntity.setUserType(data.userType());
        if(data.terminatedAt() != null) {
            userEntity.setTerminatedAt(LocalDateTime.ofInstant(data.terminatedAt(), java.time.ZoneId.systemDefault()));
        }
        return userEntity;
    }
}
