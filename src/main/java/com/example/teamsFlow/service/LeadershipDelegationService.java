package com.example.teamsFlow.service;
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
    public LeadershipDelegationService(LeadershipDelegationRepository repository) { this.repository = repository; }
    public List<LeadershipDelegation> getDelegations() { return repository.findAll(); }
    public Optional<LeadershipDelegation> getDelegationById(Long id) { return repository.findById(id); }
    public List<LeadershipDelegation> getActiveDelegationsByTeam(Long teamId) { return repository.findByTeam_IdAndIsActiveTrue(teamId); }
    @Transactional
    public LeadershipDelegation salvar(LeadershipDelegation entity) { return repository.save(entity); }
    @Transactional
    public void excluir(LeadershipDelegation entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
