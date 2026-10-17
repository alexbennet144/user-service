package com.smartdesk.backend.service.user.provision;

import com.yourorg.core.kafka.event.HrUserCreatedEvent;

public interface UserProvisioningService {

	void processEvent(HrUserCreatedEvent event);

}
