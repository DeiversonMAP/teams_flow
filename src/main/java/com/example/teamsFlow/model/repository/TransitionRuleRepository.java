package com.example.teamsFlow.model.repository;

import com.example.teamsFlow.model.entity.TransitionRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransitionRuleRepository extends JpaRepository<TransitionRule, Long> {
    List<TransitionRule> findByBoard_Id(Long boardId);
}
