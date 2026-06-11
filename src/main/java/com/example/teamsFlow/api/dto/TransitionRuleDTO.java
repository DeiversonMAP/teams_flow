package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.TransitionRule;
import com.example.teamsFlow.model.enuns.TaskStatus;
import com.example.teamsFlow.model.enuns.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransitionRuleDTO {

    private Long id;
    private TaskStatus fromStatus;
    private TaskStatus toStatus;
    private UserRole requiredRole;
    private String description;
    private Long boardId;

    public static TransitionRuleDTO create(TransitionRule rule) {
        ModelMapper modelMapper = new ModelMapper();
        TransitionRuleDTO dto = modelMapper.map(rule, TransitionRuleDTO.class);
        if (rule.getBoard() != null) {
            dto.setBoardId(rule.getBoard().getId());
        }
        return dto;
    }
}
