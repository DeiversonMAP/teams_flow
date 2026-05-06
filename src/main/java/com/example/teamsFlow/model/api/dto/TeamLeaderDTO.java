package com.example.teamsFlow.model.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeamLeaderDTO extends UserDTO {
    private Long currentTeamId;
    private Boolean isTemporaryLeader;
}