package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.Team;
import com.example.teamsFlow.model.enuns.TeamStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private Long id;
    private Integer maxMembers;
    private TeamStatus status;
    private Integer score;
    private String createdAt;
    private Long leaderId;

    public static TeamDTO create(Team team) {
        ModelMapper modelMapper = new ModelMapper();
        TeamDTO dto = modelMapper.map(team, TeamDTO.class);
        if (team.getLeader() != null) {
            dto.setLeaderId(team.getLeader().getId());
        }
        return dto;
    }
}
