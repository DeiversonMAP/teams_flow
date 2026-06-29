package com.example.teamsFlow.api.dto;
import com.example.teamsFlow.model.entity.Task;
import com.example.teamsFlow.model.enuns.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data @NoArgsConstructor @AllArgsConstructor
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
    public static TaskDTO create(Task t) {
        TaskDTO dto = new ModelMapper().map(t, TaskDTO.class);
        if (t.getSprint() != null) dto.setSprintId(t.getSprint().getId());
        if (t.getCreatedBy() != null) dto.setCreatedById(t.getCreatedBy().getId());
        if (t.getAssignedTo() != null) dto.setAssignedToId(t.getAssignedTo().getId());
        if (t.getTestedBy() != null) dto.setTestedById(t.getTestedBy().getId());
        return dto;
    }
}
