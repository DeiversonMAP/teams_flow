package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.LeadershipDelegation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

    public static LeadershipDelegationDTO create(LeadershipDelegation delegation) {
        ModelMapper modelMapper = new ModelMapper();
        LeadershipDelegationDTO dto = modelMapper.map(delegation, LeadershipDelegationDTO.class);
        if (delegation.getOriginalLeader() != null) {
            dto.setOriginalLeaderId(delegation.getOriginalLeader().getId());
        }
        if (delegation.getDelegatedTo() != null) {
            dto.setDelegatedToId(delegation.getDelegatedTo().getId());
        }
        if (delegation.getTeam() != null) {
            dto.setTeamId(delegation.getTeam().getId());
        }
        return dto;
    }
}
