package com.smartdesk.backend.kafka.listener;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.smartdesk.backend.exception.InvalidUserDataException;
import com.smartdesk.backend.exception.cognito.CognitoUnavailableException;
import com.smartdesk.backend.exception.database.DatabaseUnavailableException;
import com.smartdesk.backend.exception.database.DuplicateUserException;
import com.smartdesk.backend.exception.database.RoleNotFoundException;
import com.smartdesk.backend.exception.database.TenantNotFoundException;
import com.smartdesk.backend.service.user.provision.UserProvisioningService;
import com.yourorg.core.kafka.event.HrUserCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HrUserCreatedListenerImpl implements HrUserCreatedListener {

    private final UserProvisioningService provisioningService;

    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 2000, multiplier = 2),
            dltTopicSuffix = ".DLT",
            include = { CognitoUnavailableException.class, DatabaseUnavailableException.class }
    )
    @KafkaListener(
            topics = "hr.user.created.v1",
            groupId = "user-service-group"
    )
    @Override
    public void listen(
            HrUserCreatedEvent event,
            Acknowledgment acknowledgment) {

        try {

            provisioningService.processEvent(event);

            acknowledgment.acknowledge();

        } catch (Exception ex) {

            log.error("Error processing event {}", event.metadata().eventId(), ex);

            throw ex; // triggers retry topic
        }
    }

    @KafkaListener(topics = "hr.user.created.v1.DLT")
    public void handleDlt(HrUserCreatedEvent event) {
        log.error("Event moved to DLT: {}", event);
    }
}
