package com.example.teamsFlow.model.repository;

import com.example.teamsFlow.model.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}