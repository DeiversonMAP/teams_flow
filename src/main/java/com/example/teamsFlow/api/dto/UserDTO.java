package com.example.teamsFlow.api.dto;

import com.example.teamsFlow.model.entity.User;
import com.example.teamsFlow.model.enuns.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private String createdAt;

    public static UserDTO create(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }
}
