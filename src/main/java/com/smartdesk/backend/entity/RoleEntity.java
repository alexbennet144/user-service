package com.smartdesk.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
    name = "role",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_tenant_role_code", columnNames = {"tenant_id", "role_code"})
    }
)
@Setter
@Getter
public class RoleEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantEntity tenant;

    @Column(nullable = false)
    private String roleCode;
    
    private String description;

    @Column(name = "is_system_role", nullable = false)
    private boolean systemRole;

    @Column(nullable = false)
    private Instant createdAt;
}