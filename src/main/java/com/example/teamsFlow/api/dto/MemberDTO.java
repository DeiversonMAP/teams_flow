package com.example.teamsFlow.api.dto;
import com.example.teamsFlow.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data @NoArgsConstructor @AllArgsConstructor
public class MemberDTO extends UserDTO {
    private Long teamId;
    public static MemberDTO create(Member m) {
        MemberDTO dto = new ModelMapper().map(m, MemberDTO.class);
        if (m.getTeam() != null) dto.setTeamId(m.getTeam().getId());
        return dto;
    }
}
