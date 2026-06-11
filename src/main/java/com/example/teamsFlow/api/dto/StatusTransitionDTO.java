package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.StatusTransition;
import com.example.teamsFlow.model.enuns.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusTransitionDTO {

    private Long id;
    private TaskStatus fromStatus;
    private TaskStatus toStatus;
    private String startDate;
    private String endDate;
    private Long taskId;
    private Long changedById;

    public static StatusTransitionDTO create(StatusTransition transition) {
        ModelMapper modelMapper = new ModelMapper();
        StatusTransitionDTO dto = modelMapper.map(transition, StatusTransitionDTO.class);
        if (transition.getTask() != null) {
            dto.setTaskId(transition.getTask().getId());
        }
        if (transition.getChangedBy() != null) {
            dto.setChangedById(transition.getChangedBy().getId());
        }
        return dto;
    }
}
