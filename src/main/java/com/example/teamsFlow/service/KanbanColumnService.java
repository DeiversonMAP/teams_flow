package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
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

    public KanbanColumnService(KanbanColumnRepository repository) {
        this.repository = repository;
    }

    public List<KanbanColumn> getColumns() {
        return repository.findAll();
    }

    public Optional<KanbanColumn> getColumnById(Long id) {
        return repository.findById(id);
    }

    public List<KanbanColumn> getColumnsByBoard(Long boardId) {
        return repository.findByBoard_Id(boardId);
    }

    @Transactional
    public KanbanColumn salvar(KanbanColumn column) {
        validar(column);
        return repository.save(column);
    }

    @Transactional
    public void excluir(KanbanColumn column) {
        Objects.requireNonNull(column.getId());
        repository.delete(column);
    }

    public void validar(KanbanColumn column) {
        if (column.getName() == null || column.getName().trim().isEmpty()) {
            throw new RegraNegocioException("Nome da coluna inválido");
        }
        if (column.getStatus() == null) {
            throw new RegraNegocioException("Status da coluna inválido");
        }
        if (column.getBoard() == null || column.getBoard().getId() == null) {
            throw new RegraNegocioException("Board da coluna inválido");
        }
    }
}
