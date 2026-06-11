package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.TeamLeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamLeaderDTO extends UserDTO {

    private Long currentTeamId;
    private Boolean isTemporaryLeader;

    public static TeamLeaderDTO create(TeamLeader leader) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(leader, TeamLeaderDTO.class);
    }
}
