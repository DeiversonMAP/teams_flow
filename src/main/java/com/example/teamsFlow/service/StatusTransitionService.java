package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
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

    public StatusTransitionService(StatusTransitionRepository repository) {
        this.repository = repository;
    }

    public List<StatusTransition> getTransitions() {
        return repository.findAll();
    }

    public Optional<StatusTransition> getTransitionById(Long id) {
        return repository.findById(id);
    }

    public List<StatusTransition> getTransitionsByTask(Long taskId) {
        return repository.findByTask_Id(taskId);
    }

    @Transactional
    public StatusTransition salvar(StatusTransition transition) {
        validar(transition);
        return repository.save(transition);
    }

    @Transactional
    public void excluir(StatusTransition transition) {
        Objects.requireNonNull(transition.getId());
        repository.delete(transition);
    }

    public void validar(StatusTransition transition) {
        if (transition.getFromStatus() == null) {
            throw new RegraNegocioException("Status de origem inválido");
        }
        if (transition.getToStatus() == null) {
            throw new RegraNegocioException("Status de destino inválido");
        }
        if (transition.getTask() == null || transition.getTask().getId() == null) {
            throw new RegraNegocioException("Tarefa da transição inválida");
        }
    }
}
