package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.AuditLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

    public static AuditLogDTO create(AuditLog log) {
        ModelMapper modelMapper = new ModelMapper();
        AuditLogDTO dto = modelMapper.map(log, AuditLogDTO.class);
        if (log.getChangedBy() != null) {
            dto.setChangedById(log.getChangedBy().getId());
        }
        return dto;
    }
}
