package com.example.teamsFlow.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String changedAt;
    private String entityType;
    private Long entityId;
    private String oldValue;
    private String newValue;

    @ManyToOne
    @JoinColumn(name = "changed_by_id")
    private User changedBy;
}
