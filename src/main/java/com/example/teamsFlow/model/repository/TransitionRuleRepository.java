package com.example.teamsFlow.model.repository;

import org.springframework.stereotype.Repository;
import com.example.teamsFlow.model.entity.TransitionRule;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TransitionRuleRepository extends JpaRepository<TransitionRule, Long> {
}