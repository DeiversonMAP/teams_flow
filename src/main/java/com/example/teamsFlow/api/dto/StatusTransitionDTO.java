package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.enuns.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusTransitionDTO {
    private Long id;
    private TaskStatus fromStatus; // Enum
    private TaskStatus toStatus; // Enum
    private String startDate;
    private String endDate;

    private Long taskId;
    private Long changedById;
}