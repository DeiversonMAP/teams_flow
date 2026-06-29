package com.example.teamsFlow.service;
import com.example.teamsFlow.model.entity.KanbanBoard;
import com.example.teamsFlow.model.repository.KanbanBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class KanbanBoardService {
    private final KanbanBoardRepository repository;
    public KanbanBoardService(KanbanBoardRepository repository) { this.repository = repository; }
    public List<KanbanBoard> getBoards() { return repository.findAll(); }
    public Optional<KanbanBoard> getBoardById(Long id) { return repository.findById(id); }
    public Optional<KanbanBoard> getBoardByProject(Long projectId) { return repository.findByProject_Id(projectId); }
    @Transactional
    public KanbanBoard salvar(KanbanBoard entity) { return repository.save(entity); }
    @Transactional
    public void excluir(KanbanBoard entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
