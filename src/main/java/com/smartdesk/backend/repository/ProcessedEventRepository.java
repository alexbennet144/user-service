package com.smartdesk.backend.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartdesk.backend.entity.ProcessedEventEntity;

public interface ProcessedEventRepository 
        extends JpaRepository<ProcessedEventEntity, UUID> {

    boolean existsById(UUID id);
}
