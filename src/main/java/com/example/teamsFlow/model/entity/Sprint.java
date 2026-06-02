package com.example.teamsFlow.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id_id")
    private Project projectId;

    private String name;
    private String startDate;
    private String endDate;
    private Integer totalStoryPoints;


    public void addStoryPoints(Integer points) {
        if (points != null) {
            this.totalStoryPoints += points;
        }
    }


}

