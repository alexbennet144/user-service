package com.smartdesk.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tenant")
public class TenantEntity extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "tenant_code", nullable = false, length = 50, unique = true)
    private String tenantCode;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Inverse side of the relationship.
     * NO join column here.
     * FK lives in business_user.tenant_id
     */
    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    private List<BusinessUserEntity> users;

}

