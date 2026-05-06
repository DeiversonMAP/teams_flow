package com.example.teamsFlow.model.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadershipDelegationDTO {
    private Long id;
    private String startDate;
    private String endDate;
    private Boolean isActive;

    private Long originalLeaderId;
    private Long delegatedToId;
    private Long teamId;
}