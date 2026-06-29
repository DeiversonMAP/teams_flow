package com.example.teamsFlow.api.dto;
import com.example.teamsFlow.model.entity.LeadershipDelegation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data @NoArgsConstructor @AllArgsConstructor
public class LeadershipDelegationDTO {
    private Long id;
    private String startDate;
    private String endDate;
    private Boolean isActive;
    private Long originalLeaderId;
    private Long delegatedToId;
    private Long teamId;
    public static LeadershipDelegationDTO create(LeadershipDelegation d) {
        LeadershipDelegationDTO dto = new ModelMapper().map(d, LeadershipDelegationDTO.class);
        if (d.getOriginalLeader() != null) dto.setOriginalLeaderId(d.getOriginalLeader().getId());
        if (d.getDelegatedTo() != null) dto.setDelegatedToId(d.getDelegatedTo().getId());
        if (d.getTeam() != null) dto.setTeamId(d.getTeam().getId());
        return dto;
    }
}
