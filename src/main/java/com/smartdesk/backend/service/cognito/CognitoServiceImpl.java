package com.smartdesk.backend.service.cognito;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.smartdesk.backend.exception.cognito.CognitoPermanentException;
import com.smartdesk.backend.exception.cognito.CognitoTransientException;
import com.yourorg.core.kafka.event.HrUserCreatedPayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CodeDeliveryFailureException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InternalErrorException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InvalidParameterException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InvalidPasswordException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.LimitExceededException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.MessageActionType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.NotAuthorizedException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.TooManyRequestsException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UnsupportedUserStateException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UsernameExistsException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CognitoServiceImpl implements CognitoService {

    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${cognito.user-pool-id}")
    private String userPoolId;

    @Retryable(
        include = { CognitoTransientException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    @Override
    public void createUser(HrUserCreatedPayload payload) {

        try {

            AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(payload.email())
                    .temporaryPassword(generateTemporaryPassword())
                    .messageAction(MessageActionType.SUPPRESS)
                    .userAttributes(
                            AttributeType.builder()
                                    .name("email")
                                    .value(payload.email())
                                    .build(),
                            AttributeType.builder()
                                    .name("email_verified")
                                    .value("true")
                                    .build()
                    )
                    .build();

            cognitoClient.adminCreateUser(request);

        } catch (UsernameExistsException ex) {
            log.info("User already exists in Cognito for email={}", payload.email());
            return; // idempotent success
        } catch (TooManyRequestsException |
                 LimitExceededException |
                 InternalErrorException |
                 SdkClientException ex) {

            log.warn("Transient Cognito failure for email={}", payload.email(), ex);
            throw new CognitoTransientException("Transient Cognito failure", ex);

        } catch (InvalidParameterException |
                 InvalidPasswordException |
                 NotAuthorizedException |
                 CodeDeliveryFailureException |
                 UnsupportedUserStateException ex) {

            log.error("Permanent Cognito failure for email={}", payload.email(), ex);
            throw new CognitoPermanentException("Permanent Cognito failure", ex);
        }
    }

    @Recover
    public void recover(CognitoTransientException ex, HrUserCreatedPayload payload) {
        log.error("Cognito retry exhausted for email={}", payload.email(), ex);
        throw ex; // escalate to Kafka retry topic (Layer 3)
    }

    private String generateTemporaryPassword() {
        return "Temp@1234"; // Replace with secure generator
    }
}