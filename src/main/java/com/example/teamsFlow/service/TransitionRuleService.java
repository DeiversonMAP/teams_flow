package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.TransitionRule;
import com.example.teamsFlow.model.repository.TransitionRuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransitionRuleService {

    private final TransitionRuleRepository repository;

    public TransitionRuleService(TransitionRuleRepository repository) {
        this.repository = repository;
    }

    public List<TransitionRule> getRules() {
        return repository.findAll();
    }

    public Optional<TransitionRule> getRuleById(Long id) {
        return repository.findById(id);
    }

    public List<TransitionRule> getRulesByBoard(Long boardId) {
        return repository.findByBoard_Id(boardId);
    }

    @Transactional
    public TransitionRule salvar(TransitionRule rule) {
        validar(rule);
        return repository.save(rule);
    }

    @Transactional
    public void excluir(TransitionRule rule) {
        Objects.requireNonNull(rule.getId());
        repository.delete(rule);
    }

    public void validar(TransitionRule rule) {
        if (rule.getFromStatus() == null) {
            throw new RegraNegocioException("Status de origem inválido");
        }
        if (rule.getToStatus() == null) {
            throw new RegraNegocioException("Status de destino inválido");
        }
        if (rule.getRequiredRole() == null) {
            throw new RegraNegocioException("Role necessária inválida");
        }
        if (rule.getBoard() == null || rule.getBoard().getId() == null) {
            throw new RegraNegocioException("Board da regra inválido");
        }
    }
}
