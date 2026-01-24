package com.smartdesk.backend.entity;

import java.time.Instant;

import com.smartdesk.backend.constant.enums.UserStatus;
import com.smartdesk.backend.constant.enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "business_user")
@Getter
@Setter
public class BusinessUserEntity extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tenant_id", nullable = false)
	private TenantEntity tenant;

	@Column(name = "employee_id")
	private String employeeId;

	private String email;

	@Column(name = "display_name")
	private String displayName;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@Column(name = "user_type")
	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Column(name = "terminated_at")
	private Instant terminatedAt;

}
