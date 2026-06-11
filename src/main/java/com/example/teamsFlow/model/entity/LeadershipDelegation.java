package com.example.teamsFlow.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadershipDelegation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startDate;
    private String endDate;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "original_leader_id")
    private User originalLeader;

    @ManyToOne
    @JoinColumn(name = "delegated_to_id")
    private User delegatedTo;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public boolean isCurrentlyActive(String currentDate) {
        return currentDate.compareTo(startDate) >= 0
                && currentDate.compareTo(endDate) <= 0;
    }
}
