package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.KanbanBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KanbanBoardDTO {

    private Long id;
    private Long projectId;

    public static KanbanBoardDTO create(KanbanBoard board) {
        ModelMapper modelMapper = new ModelMapper();
        KanbanBoardDTO dto = modelMapper.map(board, KanbanBoardDTO.class);
        if (board.getProject() != null) {
            dto.setProjectId(board.getProject().getId());
        }
        return dto;
    }
}
