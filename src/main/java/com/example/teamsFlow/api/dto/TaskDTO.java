package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.Task;
import com.example.teamsFlow.model.enuns.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

    public static TaskDTO create(Task task) {
        ModelMapper modelMapper = new ModelMapper();
        TaskDTO dto = modelMapper.map(task, TaskDTO.class);
        if (task.getSprint() != null) {
            dto.setSprintId(task.getSprint().getId());
        }
        if (task.getCreatedBy() != null) {
            dto.setCreatedById(task.getCreatedBy().getId());
        }
        if (task.getAssignedTo() != null) {
            dto.setAssignedToId(task.getAssignedTo().getId());
        }
        if (task.getTestedBy() != null) {
            dto.setTestedById(task.getTestedBy().getId());
        }
        return dto;
    }
}
