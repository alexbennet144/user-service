package com.smartdesk.backend.service.user.provision;


import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartdesk.backend.entity.ProcessedEventEntity;
import com.smartdesk.backend.entity.RoleEntity;
import com.smartdesk.backend.entity.TenantEntity;
import com.smartdesk.backend.entity.UserEntity;
import com.smartdesk.backend.entity.UserRoleEntity;
import com.smartdesk.backend.exception.database.DatabaseUnavailableException;
import com.smartdesk.backend.exception.database.DuplicateUserException;
import com.smartdesk.backend.exception.database.RoleNotFoundException;
import com.smartdesk.backend.exception.database.TenantNotFoundException;
import com.smartdesk.backend.repository.ProcessedEventRepository;
import com.smartdesk.backend.repository.RoleRepository;
import com.smartdesk.backend.repository.TenantRepository;
import com.smartdesk.backend.repository.UserRepository;
import com.smartdesk.backend.repository.UserRoleRepository;
import com.smartdesk.backend.service.cognito.CognitoService;
import com.yourorg.core.kafka.event.HrUserCreatedEvent;
import com.yourorg.core.kafka.event.HrUserCreatedPayload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProvisioningServiceImpl implements UserProvisioningService {

    private final ProcessedEventRepository processedEventRepository;
    private final CognitoService cognitoService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final TenantRepository tenantRepository;



    @Transactional
    @Override
    public void processEvent(HrUserCreatedEvent event) {

        UUID eventId = event.metadata().eventId();

        if (processedEventRepository.existsById(eventId)) {
            return;
        }

        HrUserCreatedPayload payload = event.data();

        // 1️⃣ Create in Cognito
        cognitoService.createUser(payload);

        // 2️⃣ Save locally
        TenantEntity tenant = tenantRepository.findByTenantCode(payload.tenantCode())
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found: " + payload.tenantCode()));
        
        try {
            UserEntity userEntity = UserEntity.from(payload, tenant.getId());
            userRepository.save(userEntity);

            // 3️⃣ Assign role
            RoleEntity roleEntity = roleRepository.findByRoleCode("SMARTDESK_USER")
                    .orElseThrow(() -> new RoleNotFoundException("SMARTDESK_USER role not found"));
            
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUser(userEntity);
            userRoleEntity.setRole(roleEntity);
            userRoleEntity.setAssignedAt(java.time.Instant.now());
            userRoleEntity.setAssignedBy(UUID.fromString("00000000-0000-0000-0000-000000000000"));
            userRoleRepository.save(userRoleEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateUserException("User with the same employee ID or email already exists", ex);
        } catch (DataAccessException ex) {
            throw new DatabaseUnavailableException("Error saving user data to the database", ex);
        }

        // 4️⃣ Mark event processed
        processedEventRepository.save(new ProcessedEventEntity(eventId));
    }
}