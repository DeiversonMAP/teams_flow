package com.example.teamsFlow.service;
import com.example.teamsFlow.model.entity.Sprint;
import com.example.teamsFlow.model.repository.SprintRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SprintService {
    private final SprintRepository repository;
    public SprintService(SprintRepository repository) { this.repository = repository; }
    public List<Sprint> getSprints() { return repository.findAll(); }
    public Optional<Sprint> getSprintById(Long id) { return repository.findById(id); }
    public List<Sprint> getSprintsByProject(Long projectId) { return repository.findByProject_Id(projectId); }
    @Transactional
    public Sprint salvar(Sprint entity) { return repository.save(entity); }
    @Transactional
    public void excluir(Sprint entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
