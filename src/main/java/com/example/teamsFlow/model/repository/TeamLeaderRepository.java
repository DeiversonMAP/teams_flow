package com.example.teamsFlow.model.repository;

import com.example.teamsFlow.model.entity.TeamLeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TeamLeaderRepository extends JpaRepository<TeamLeader, Long> {
}

