package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Project;
import com.example.teamsFlow.model.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public List<Project> getProjects() {
        return repository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return repository.findById(id);
    }

    public List<Project> getProjectsByTeam(Long teamId) {
        return repository.findByTeam_Id(teamId);
    }

    @Transactional
    public Project salvar(Project project) {
        validar(project);
        return repository.save(project);
    }

    @Transactional
    public void excluir(Project project) {
        Objects.requireNonNull(project.getId());
        repository.delete(project);
    }

    public void validar(Project project) {
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new RegraNegocioException("Nome do projeto inválido");
        }
        if (project.getDeadline() == null || project.getDeadline().trim().isEmpty()) {
            throw new RegraNegocioException("Prazo do projeto inválido");
        }
        if (project.getTeam() == null || project.getTeam().getId() == null) {
            throw new RegraNegocioException("Equipe do projeto inválida");
        }
    }
}
