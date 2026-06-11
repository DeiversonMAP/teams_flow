package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.LeadershipDelegationDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.LeadershipDelegation;
import com.example.teamsFlow.model.entity.Team;
import com.example.teamsFlow.model.entity.User;
import com.example.teamsFlow.service.LeadershipDelegationService;
import com.example.teamsFlow.service.TeamService;
import com.example.teamsFlow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/delegations")
@RequiredArgsConstructor
@CrossOrigin
public class LeadershipDelegationController {

    private final LeadershipDelegationService service;
    private final UserService userService;
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity get() {
        List<LeadershipDelegation> delegations = service.getDelegations();
        return ResponseEntity.ok(delegations.stream().map(LeadershipDelegationDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<LeadershipDelegation> delegation = service.getDelegationById(id);
        if (!delegation.isPresent()) {
            return new ResponseEntity("Delegação não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(delegation.map(LeadershipDelegationDTO::create));
    }

    @GetMapping("/team/{teamId}/active")
    public ResponseEntity getActiveByTeam(@PathVariable("teamId") Long teamId) {
        List<LeadershipDelegation> delegations = service.getActiveDelegationsByTeam(teamId);
        return ResponseEntity.ok(delegations.stream().map(LeadershipDelegationDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody LeadershipDelegationDTO dto) {
        try {
            LeadershipDelegation delegation = converter(dto);
            delegation = service.salvar(delegation);
            return new ResponseEntity(delegation, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LeadershipDelegationDTO dto) {
        if (!service.getDelegationById(id).isPresent()) {
            return new ResponseEntity("Delegação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            LeadershipDelegation delegation = converter(dto);
            delegation.setId(id);
            service.salvar(delegation);
            return ResponseEntity.ok(delegation);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<LeadershipDelegation> delegation = service.getDelegationById(id);
        if (!delegation.isPresent()) {
            return new ResponseEntity("Delegação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(delegation.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public LeadershipDelegation converter(LeadershipDelegationDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        LeadershipDelegation delegation = modelMapper.map(dto, LeadershipDelegation.class);
        if (dto.getOriginalLeaderId() != null) {
            Optional<User> leader = userService.getUserById(dto.getOriginalLeaderId());
            delegation.setOriginalLeader(leader.orElse(null));
        }
        if (dto.getDelegatedToId() != null) {
            Optional<User> delegated = userService.getUserById(dto.getDelegatedToId());
            delegation.setDelegatedTo(delegated.orElse(null));
        }
        if (dto.getTeamId() != null) {
            Optional<Team> team = teamService.getTeamById(dto.getTeamId());
            delegation.setTeam(team.orElse(null));
        }
        return delegation;
    }
}
