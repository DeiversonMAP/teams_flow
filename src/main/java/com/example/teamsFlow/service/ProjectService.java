package com.example.teamsFlow.service;
import com.example.teamsFlow.model.entity.Project;
import com.example.teamsFlow.model.repository.ProjectRepository;
import com.example.teamsFlow.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository repository;
    public ProjectService(ProjectRepository repository) { this.repository = repository; }
    public List<Project> getProjects() { return repository.findAll(); }
    public Optional<Project> getProjectById(Long id) { return repository.findById(id); }
    public List<Project> getProjectsByTeam(Long teamId) { return repository.findByTeam_Id(teamId); }
    @Transactional
    public Project salvar(Project entity) { return repository.save(entity); }
    @Transactional
    public void excluir(Project entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
