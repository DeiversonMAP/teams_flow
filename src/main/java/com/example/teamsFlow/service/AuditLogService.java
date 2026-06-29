package com.example.teamsFlow.service;
import com.example.teamsFlow.model.entity.AuditLog;
import com.example.teamsFlow.model.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuditLogService {
    private final AuditLogRepository repository;
    public AuditLogService(AuditLogRepository repository) { this.repository = repository; }
    public List<AuditLog> getLogs() { return repository.findAll(); }
    public Optional<AuditLog> getLogById(Long id) { return repository.findById(id); }
    public List<AuditLog> getLogsByEntity(String entityType, Long entityId) { return repository.findByEntityTypeAndEntityId(entityType, entityId); }
    @Transactional
    public AuditLog salvar(AuditLog entity) { return repository.save(entity); }
    @Transactional
    public void excluir(AuditLog entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
