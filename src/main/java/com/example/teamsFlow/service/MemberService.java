package com.example.teamsFlow.service;
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
    public MemberService(MemberRepository repository) { this.repository = repository; }
    public List<Member> getMembers() { return repository.findAll(); }
    public Optional<Member> getMemberById(Long id) { return repository.findById(id); }
    public List<Member> getMembersByTeam(Long teamId) { return repository.findByTeam_Id(teamId); }
    @Transactional
    public Member salvar(Member entity) { return repository.save(entity); }
    @Transactional
    public void excluir(Member entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
