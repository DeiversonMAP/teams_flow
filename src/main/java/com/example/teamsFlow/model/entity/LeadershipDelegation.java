package com.example.teamsFlow.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private Long leaderId;
    private Long delegatedToId;
    private String startDate;
    private String endDate;

    public boolean isActive(String currentDate) {
        return currentDate.compareTo(startDate) >= 0
            && currentDate.compareTo(endDate) <= 0;
    }
}