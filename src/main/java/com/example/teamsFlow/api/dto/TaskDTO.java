package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.enuns.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String createdAt;
    private Integer storyPoints;
    private TaskStatus currentStatus;

    private Long sprintId;
    private Long createdById;
    private Long assignedToId;
    private Long testedById;
}
