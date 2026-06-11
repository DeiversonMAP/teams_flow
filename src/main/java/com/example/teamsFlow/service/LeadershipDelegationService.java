package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.LeadershipDelegation;
import com.example.teamsFlow.model.repository.LeadershipDelegationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LeadershipDelegationService {

    private final LeadershipDelegationRepository repository;

    public LeadershipDelegationService(LeadershipDelegationRepository repository) {
        this.repository = repository;
    }

    public List<LeadershipDelegation> getDelegations() {
        return repository.findAll();
    }

    public Optional<LeadershipDelegation> getDelegationById(Long id) {
        return repository.findById(id);
    }

    public List<LeadershipDelegation> getActiveDelegationsByTeam(Long teamId) {
        return repository.findByTeam_IdAndIsActiveTrue(teamId);
    }

    @Transactional
    public LeadershipDelegation salvar(LeadershipDelegation delegation) {
        validar(delegation);
        return repository.save(delegation);
    }

    @Transactional
    public void excluir(LeadershipDelegation delegation) {
        Objects.requireNonNull(delegation.getId());
        repository.delete(delegation);
    }

    public void validar(LeadershipDelegation delegation) {
        if (delegation.getOriginalLeader() == null || delegation.getOriginalLeader().getId() == null) {
            throw new RegraNegocioException("Líder original inválido");
        }
        if (delegation.getDelegatedTo() == null || delegation.getDelegatedTo().getId() == null) {
            throw new RegraNegocioException("Usuário delegado inválido");
        }
        if (delegation.getTeam() == null || delegation.getTeam().getId() == null) {
            throw new RegraNegocioException("Equipe da delegação inválida");
        }
        if (delegation.getStartDate() == null || delegation.getStartDate().trim().isEmpty()) {
            throw new RegraNegocioException("Data de início da delegação inválida");
        }
        if (delegation.getEndDate() == null || delegation.getEndDate().trim().isEmpty()) {
            throw new RegraNegocioException("Data de fim da delegação inválida");
        }
    }
}
