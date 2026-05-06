package com.example.teamsFlow.model.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDTO {
    private Long id;
    private String changedAt;
    private String entityType;
    private Long entityId;
    private String oldValue;
    private String newValue;

    private Long changedById;
}