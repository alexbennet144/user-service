package com.smartdesk.backend.kafka.listener;

import org.springframework.kafka.support.Acknowledgment;

import com.yourorg.core.kafka.event.HrUserCreatedEvent;

public interface HrUserCreatedListener {

	void listen(HrUserCreatedEvent event, Acknowledgment acknowledgment);

}
