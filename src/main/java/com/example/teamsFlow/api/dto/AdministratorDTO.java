package com.example.teamsFlow.api.dto;
import com.example.teamsFlow.model.entity.Administrator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data @NoArgsConstructor
public class AdministratorDTO extends UserDTO {
    public static AdministratorDTO create(Administrator a) { return new ModelMapper().map(a, AdministratorDTO.class); }
}
