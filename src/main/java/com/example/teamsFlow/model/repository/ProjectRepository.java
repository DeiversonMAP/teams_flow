package com.example.teamsFlow.model.repository;
import com.example.teamsFlow.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByTeam_Id(Long teamId);
}
