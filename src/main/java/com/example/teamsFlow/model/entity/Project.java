package com.example.teamsFlow.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String deadline;
    private Integer totalStoryPoints;

    @ManyToOne
    @JoinColumn(name = "team_id_id")
    private Team teamId;

    public void addStoryPoints(Integer points) {
        if (points != null) {
            this.totalStoryPoints += points;
        }
    }

    public boolean isOverdue(String currentDate) {
        return deadline.compareTo(currentDate) < 0;
    }

}
