package com.smartdesk.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO returned by HelloController.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloResponse {
    private String message;
}
