package com.example.teamsFlow.service;

import com.example.teamsFlow.model.entity.Team;
import com.example.teamsFlow.model.repository.TeamRepository;
import com.example.teamsFlow.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository repository;
    public TeamService(TeamRepository repository) { this.repository = repository; }
    public List<Team> getTeams() { return repository.findAll(); }
    public Optional<Team> getTeamById(Long id) { return repository.findById(id); }
    @Transactional
    public Team salvar(Team entity) { return repository.save(entity); }
    @Transactional
    public void excluir(Team entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
