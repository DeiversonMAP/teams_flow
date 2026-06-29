package com.example.teamsFlow.api.dto;
import com.example.teamsFlow.model.entity.TransitionRule;
import com.example.teamsFlow.model.enuns.TaskStatus;
import com.example.teamsFlow.model.enuns.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data @NoArgsConstructor @AllArgsConstructor
public class TransitionRuleDTO {
    private Long id;
    private TaskStatus fromStatus;
    private TaskStatus toStatus;
    private UserRole requiredRole;
    private String description;
    private Long boardId;
    public static TransitionRuleDTO create(TransitionRule r) {
        TransitionRuleDTO dto = new ModelMapper().map(r, TransitionRuleDTO.class);
        if (r.getBoard() != null) dto.setBoardId(r.getBoard().getId());
        return dto;
    }
}
