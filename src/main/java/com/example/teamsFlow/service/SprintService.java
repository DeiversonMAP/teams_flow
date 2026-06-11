package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
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

    public SprintService(SprintRepository repository) {
        this.repository = repository;
    }

    public List<Sprint> getSprints() {
        return repository.findAll();
    }

    public Optional<Sprint> getSprintById(Long id) {
        return repository.findById(id);
    }

    public List<Sprint> getSprintsByProject(Long projectId) {
        return repository.findByProject_Id(projectId);
    }

    @Transactional
    public Sprint salvar(Sprint sprint) {
        validar(sprint);
        return repository.save(sprint);
    }

    @Transactional
    public void excluir(Sprint sprint) {
        Objects.requireNonNull(sprint.getId());
        repository.delete(sprint);
    }

    public void validar(Sprint sprint) {
        if (sprint.getName() == null || sprint.getName().trim().isEmpty()) {
            throw new RegraNegocioException("Nome da sprint inválido");
        }
        if (sprint.getStartDate() == null || sprint.getStartDate().trim().isEmpty()) {
            throw new RegraNegocioException("Data de início inválida");
        }
        if (sprint.getEndDate() == null || sprint.getEndDate().trim().isEmpty()) {
            throw new RegraNegocioException("Data de fim inválida");
        }
        if (sprint.getProject() == null || sprint.getProject().getId() == null) {
            throw new RegraNegocioException("Projeto da sprint inválido");
        }
    }
}
