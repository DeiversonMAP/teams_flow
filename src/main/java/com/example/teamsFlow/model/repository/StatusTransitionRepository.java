package com.example.teamsFlow.model.repository;
import com.example.teamsFlow.model.entity.StatusTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StatusTransitionRepository extends JpaRepository<StatusTransition, Long> {
    List<StatusTransition> findByTask_Id(Long taskId);
}
