package com.example.teamsFlow.service;
import com.example.teamsFlow.model.entity.KanbanColumn;
import com.example.teamsFlow.model.repository.KanbanColumnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class KanbanColumnService {
    private final KanbanColumnRepository repository;
    public KanbanColumnService(KanbanColumnRepository repository) { this.repository = repository; }
    public List<KanbanColumn> getColumns() { return repository.findAll(); }
    public Optional<KanbanColumn> getColumnById(Long id) { return repository.findById(id); }
    public List<KanbanColumn> getColumnsByBoard(Long boardId) { return repository.findByBoard_Id(boardId); }
    @Transactional
    public KanbanColumn salvar(KanbanColumn entity) { return repository.save(entity); }
    @Transactional
    public void excluir(KanbanColumn entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
