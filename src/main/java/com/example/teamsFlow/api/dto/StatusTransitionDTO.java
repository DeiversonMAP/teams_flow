package com.example.teamsFlow.api.dto;
import com.example.teamsFlow.model.entity.StatusTransition;
import com.example.teamsFlow.model.enuns.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data @NoArgsConstructor @AllArgsConstructor
public class StatusTransitionDTO {
    private Long id;
    private TaskStatus fromStatus;
    private TaskStatus toStatus;
    private String startDate;
    private String endDate;
    private Long taskId;
    private Long changedById;
    public static StatusTransitionDTO create(StatusTransition s) {
        StatusTransitionDTO dto = new ModelMapper().map(s, StatusTransitionDTO.class);
        if (s.getTask() != null) dto.setTaskId(s.getTask().getId());
        if (s.getChangedBy() != null) dto.setChangedById(s.getChangedBy().getId());
        return dto;
    }
}
