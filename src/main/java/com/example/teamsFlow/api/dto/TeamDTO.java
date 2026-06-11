package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.enuns.TeamStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Long> projectIds;
}
