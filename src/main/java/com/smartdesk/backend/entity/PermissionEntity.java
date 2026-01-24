package com.smartdesk.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "permission",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_permission_code", columnNames = "permission_code")
    }
)
@Getter
@Setter
public class PermissionEntity extends BaseEntity {

    @Column(nullable = false)
    private String permissionCode;

    @Column(nullable = false)
    private String resource;

    @Column(nullable = false)
    private String action;

    private String description;
}