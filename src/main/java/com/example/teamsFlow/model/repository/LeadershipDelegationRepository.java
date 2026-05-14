package com.example.teamsFlow.model.repository;

import com.example.teamsFlow.model.entity.LeadershipDelegation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadershipDelegationRepository extends JpaRepository<LeadershipDelegation, Long> {
    List<LeadershipDelegation> findByForTeamIdAndIsActiveTrue(Long teamId);
}