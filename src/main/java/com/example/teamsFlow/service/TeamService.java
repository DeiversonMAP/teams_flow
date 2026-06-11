package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Team;
import com.example.teamsFlow.model.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public List<Team> getTeams() {
        return repository.findAll();
    }

    public Optional<Team> getTeamById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Team salvar(Team team) {
        validar(team);
        return repository.save(team);
    }

    @Transactional
    public void excluir(Team team) {
        Objects.requireNonNull(team.getId());
        repository.delete(team);
    }

    public void validar(Team team) {
        if (team.getMaxMembers() == null || team.getMaxMembers() <= 0) {
            throw new RegraNegocioException("Número máximo de membros inválido");
        }
        if (team.getStatus() == null) {
            throw new RegraNegocioException("Status da equipe inválido");
        }
    }
}
