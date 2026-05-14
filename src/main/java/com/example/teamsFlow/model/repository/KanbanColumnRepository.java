package com.example.teamsFlow.model.repository;

import com.example.teamsFlow.model.entity.KanbanColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanColumnRepository extends JpaRepository<KanbanColumn, Long> {
}