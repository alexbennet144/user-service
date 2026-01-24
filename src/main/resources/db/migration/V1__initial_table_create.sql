-- =========================================================
-- Enable UUID generation
-- =========================================================
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- =========================================================
-- TENANT
-- =========================================================
CREATE TABLE tenant (
    id UUID PRIMARY KEY,
    tenant_code VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT uk_tenant_code UNIQUE (tenant_code)
);

-- =========================================================
-- BUSINESS USER
-- =========================================================
CREATE TABLE business_user (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL,
    employee_id VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    display_name VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    user_type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    terminated_at TIMESTAMP,

    CONSTRAINT uk_business_user_employee_id UNIQUE (employee_id),
    CONSTRAINT fk_business_user_tenant
        FOREIGN KEY (tenant_id)
        REFERENCES tenant (id)
);

CREATE INDEX idx_business_user_tenant
    ON business_user (tenant_id);

CREATE INDEX idx_business_user_email
    ON business_user (email);

-- =========================================================
-- ROLE
-- =========================================================
CREATE TABLE role (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL,
    role_code VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    is_system_role BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT uk_role_tenant_code UNIQUE (tenant_id, role_code),
    CONSTRAINT fk_role_tenant
        FOREIGN KEY (tenant_id)
        REFERENCES tenant (id)
);

-- =========================================================
-- PERMISSION
-- =========================================================
CREATE TABLE permission (
    id UUID PRIMARY KEY,
    permission_code VARCHAR(150) NOT NULL,
    resource VARCHAR(100) NOT NULL,
    action VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT uk_permission_code UNIQUE (permission_code)
);

-- =========================================================
-- ROLE_PERMISSION
-- =========================================================
CREATE TABLE role_permission (
    id UUID PRIMARY KEY,
    role_id UUID NOT NULL,
    permission_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT uk_role_permission UNIQUE (role_id, permission_id),
    CONSTRAINT fk_role_permission_role
        FOREIGN KEY (role_id)
        REFERENCES role (id),
    CONSTRAINT fk_role_permission_permission
        FOREIGN KEY (permission_id)
        REFERENCES permission (id)
);

CREATE INDEX idx_role_permission_role
    ON role_permission (role_id);

CREATE INDEX idx_role_permission_permission
    ON role_permission (permission_id);

-- =========================================================
-- USER_ROLE (Assignment History)
-- =========================================================
CREATE TABLE user_role (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    assigned_by UUID NOT NULL,
    assigned_at TIMESTAMP NOT NULL,
    revoked_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_user_role_user
        FOREIGN KEY (user_id)
        REFERENCES business_user (id),
    CONSTRAINT fk_user_role_role
        FOREIGN KEY (role_id)
        REFERENCES role (id)
);

CREATE INDEX idx_user_role_user
    ON user_role (user_id);

CREATE INDEX idx_user_role_active
    ON user_role (user_id)
    WHERE revoked_at IS NULL;

-- =========================================================
-- USER_ROLE_DELEGATION (TEMPORARY ACCESS)
-- =========================================================
CREATE TABLE user_role_delegation (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    valid_from TIMESTAMP NOT NULL,
    valid_to TIMESTAMP NOT NULL,
    reason VARCHAR(255),
    granted_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_delegation_user
        FOREIGN KEY (user_id)
        REFERENCES business_user (id),
    CONSTRAINT fk_delegation_role
        FOREIGN KEY (role_id)
        REFERENCES role (id)
);

CREATE INDEX idx_delegation_user
    ON user_role_delegation (user_id);

-- =========================================================
-- HR_PROVISIONING_EVENT
-- =========================================================
CREATE TABLE hr_provisioning_event (
    id UUID PRIMARY KEY,
    external_event_id VARCHAR(100) NOT NULL,
    employee_id VARCHAR(100) NOT NULL,
    event_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    received_at TIMESTAMP NOT NULL,
    processed_at TIMESTAMP,
    error_message TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE INDEX idx_hr_event_employee
    ON hr_provisioning_event (employee_id);

CREATE INDEX idx_hr_event_status
    ON hr_provisioning_event (status);

-- =========================================================
-- AUDIT_OUTBOX (OUTBOX PATTERN)
-- =========================================================
CREATE TABLE audit_outbox (
    id UUID PRIMARY KEY,
    event_type VARCHAR(100) NOT NULL,
    payload JSONB NOT NULL,
    tenant_id UUID NOT NULL,
    actor_user_id UUID,
    target_user_id UUID,
    correlation_id VARCHAR(100),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    published_at TIMESTAMP,

    CONSTRAINT fk_audit_outbox_tenant
        FOREIGN KEY (tenant_id)
        REFERENCES tenant (id)
);

CREATE INDEX idx_audit_outbox_unpublished
    ON audit_outbox (published_at)
    WHERE published_at IS NULL;

-- =========================================================
-- OPTIONAL READ-OPTIMIZED VIEW
-- =========================================================
CREATE VIEW user_profile_view AS
SELECT
    id AS user_id,
    employee_id,
    email,
    display_name,
    status
FROM business_user;
