package com.example.teamsFlow.api.dto;
import com.example.teamsFlow.model.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String deadline;
    private Integer totalStoryPoints;
    private Long teamId;
    public static ProjectDTO create(Project p) {
        ProjectDTO dto = new ModelMapper().map(p, ProjectDTO.class);
        if (p.getTeam() != null) dto.setTeamId(p.getTeam().getId());
        return dto;
    }
}
