package com.example.teamsFlow.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("MEMBER")
@Data
@NoArgsConstructor
public class Member extends User {

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
