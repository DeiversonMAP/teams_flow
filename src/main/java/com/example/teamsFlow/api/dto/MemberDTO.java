package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO extends UserDTO {

    private Long teamId;

    public static MemberDTO create(Member member) {
        ModelMapper modelMapper = new ModelMapper();
        MemberDTO dto = modelMapper.map(member, MemberDTO.class);
        if (member.getTeam() != null) {
            dto.setTeamId(member.getTeam().getId());
        }
        return dto;
    }
}
