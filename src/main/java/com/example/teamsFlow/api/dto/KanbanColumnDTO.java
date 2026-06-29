package com.example.teamsFlow.api.dto;
import com.example.teamsFlow.model.entity.KanbanColumn;
import com.example.teamsFlow.model.enuns.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data @NoArgsConstructor @AllArgsConstructor
public class KanbanColumnDTO {
    private Long id;
    private String name;
    private Integer position;
    private TaskStatus status;
    private Long boardId;
    public static KanbanColumnDTO create(KanbanColumn c) {
        KanbanColumnDTO dto = new ModelMapper().map(c, KanbanColumnDTO.class);
        if (c.getBoard() != null) dto.setBoardId(c.getBoard().getId());
        return dto;
    }
}
