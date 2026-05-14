package com.example.teamsFlow.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogRepository, Long> {
    // Busca o histórico de alterações de uma entidade específica (ex: Task, Project)
    List<AuditLogRepository> findByEntityTypeAndEntityId(String entityType, Long entityId);
}