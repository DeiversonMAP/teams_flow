package com.example.teamsFlow.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Administrator extends User {
    public boolean canManageUsers() {
        return true;
    }

    public boolean canManageTeams() {
        return true;
    }
}
