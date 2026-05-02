package com.example.teamsFlow.model.entity;

import com.example.teamsFlow.model.enuns.TeamStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer maxMembers;
    private TeamStatus status;
    private Integer score;
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "leader_id_id")
    private User leaderId;

//    private getMemberCount()    int
}


