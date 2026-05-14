package com.example.teamsFlow.model.repository;

import com.example.teamsFlow.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // Exemplo de busca customizada: Buscar todos os membros de uma equipe específica
    List<Member> findByTeamId(Long teamId);
}

