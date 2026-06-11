package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.KanbanColumn;
import com.example.teamsFlow.model.enuns.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KanbanColumnDTO {

    private Long id;
    private String name;
    private Integer position;
    private TaskStatus status;
    private Long boardId;

    public static KanbanColumnDTO create(KanbanColumn column) {
        ModelMapper modelMapper = new ModelMapper();
        KanbanColumnDTO dto = modelMapper.map(column, KanbanColumnDTO.class);
        if (column.getBoard() != null) {
            dto.setBoardId(column.getBoard().getId());
        }
        return dto;
    }
}
