package com.example.teamsFlow.api.dto;
import com.example.teamsFlow.model.entity.KanbanBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data @NoArgsConstructor @AllArgsConstructor
public class KanbanBoardDTO {
    private Long id;
    private Long projectId;
    public static KanbanBoardDTO create(KanbanBoard b) {
        KanbanBoardDTO dto = new ModelMapper().map(b, KanbanBoardDTO.class);
        if (b.getProject() != null) dto.setProjectId(b.getProject().getId());
        return dto;
    }
}
