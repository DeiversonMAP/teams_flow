package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Member;
import com.example.teamsFlow.model.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public List<Member> getMembers() {
        return repository.findAll();
    }

    public Optional<Member> getMemberById(Long id) {
        return repository.findById(id);
    }

    public List<Member> getMembersByTeam(Long teamId) {
        return repository.findByTeam_Id(teamId);
    }

    @Transactional
    public Member salvar(Member member) {
        validar(member);
        return repository.save(member);
    }

    @Transactional
    public void excluir(Member member) {
        Objects.requireNonNull(member.getId());
        repository.delete(member);
    }

    public void validar(Member member) {
        if (member.getName() == null || member.getName().trim().isEmpty()) {
            throw new RegraNegocioException("Nome do membro inválido");
        }
        if (member.getEmail() == null || member.getEmail().trim().isEmpty()) {
            throw new RegraNegocioException("Email do membro inválido");
        }
        if (member.getTeam() == null || member.getTeam().getId() == null) {
            throw new RegraNegocioException("Equipe do membro inválida");
        }
    }
}
