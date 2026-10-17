package com.smartdesk.backend.service.cognito;

import com.yourorg.core.kafka.event.HrUserCreatedPayload;

public interface CognitoService  {

	void createUser(HrUserCreatedPayload payload);

}
