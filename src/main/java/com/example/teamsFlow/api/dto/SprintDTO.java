package com.example.teamsFlow.api.dto;
import com.example.teamsFlow.model.entity.Sprint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data @NoArgsConstructor @AllArgsConstructor
public class SprintDTO {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private Integer totalStoryPoints;
    private Long projectId;
    public static SprintDTO create(Sprint s) {
        SprintDTO dto = new ModelMapper().map(s, SprintDTO.class);
        if (s.getProject() != null) dto.setProjectId(s.getProject().getId());
        return dto;
    }
}
