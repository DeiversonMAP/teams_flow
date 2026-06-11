package com.example.teamsFlow.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ADMINISTRATOR")
@Data
@NoArgsConstructor
public class Administrator extends User {

    public boolean canManageUsers() {
        return true;
    }

    public boolean canManageTeams() {
        return true;
    }
}
