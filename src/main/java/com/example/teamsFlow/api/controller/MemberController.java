package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.MemberDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Member;
import com.example.teamsFlow.model.entity.Team;
import com.example.teamsFlow.service.MemberService;
import com.example.teamsFlow.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    private final MemberService service;
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity get() {
        List<Member> members = service.getMembers();
        return ResponseEntity.ok(members.stream().map(MemberDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Member> member = service.getMemberById(id);
        if (!member.isPresent()) {
            return new ResponseEntity("Membro não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(member.map(MemberDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody MemberDTO dto) {
        try {
            Member member = converter(dto);
            member = service.salvar(member);
            return new ResponseEntity(member, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody MemberDTO dto) {
        if (!service.getMemberById(id).isPresent()) {
            return new ResponseEntity("Membro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Member member = converter(dto);
            member.setId(id);
            service.salvar(member);
            return ResponseEntity.ok(member);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Member> member = service.getMemberById(id);
        if (!member.isPresent()) {
            return new ResponseEntity("Membro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(member.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Member converter(MemberDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Member member = modelMapper.map(dto, Member.class);
        if (dto.getTeamId() != null) {
            Optional<Team> team = teamService.getTeamById(dto.getTeamId());
            member.setTeam(team.orElse(null));
        }
        return member;
    }
}
