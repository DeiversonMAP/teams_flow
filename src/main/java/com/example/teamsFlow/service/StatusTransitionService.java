package com.example.teamsFlow.service;
import com.example.teamsFlow.model.entity.StatusTransition;
import com.example.teamsFlow.model.repository.StatusTransitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StatusTransitionService {
    private final StatusTransitionRepository repository;
    public StatusTransitionService(StatusTransitionRepository repository) { this.repository = repository; }
    public List<StatusTransition> getTransitions() { return repository.findAll(); }
    public Optional<StatusTransition> getTransitionById(Long id) { return repository.findById(id); }
    public List<StatusTransition> getTransitionsByTask(Long taskId) { return repository.findByTask_Id(taskId); }
    @Transactional
    public StatusTransition salvar(StatusTransition entity) { return repository.save(entity); }
    @Transactional
    public void excluir(StatusTransition entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
