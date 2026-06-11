package com.example.teamsFlow.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("TEAM_LEADER")
@Data
@NoArgsConstructor
public class TeamLeader extends User {

    private Long currentTeamId;
    private Boolean isTemporaryLeader;

    public boolean canManageTeam() {
        return true;
    }
}
