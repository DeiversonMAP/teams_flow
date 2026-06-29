package com.example.teamsFlow.service;
import com.example.teamsFlow.model.entity.TransitionRule;
import com.example.teamsFlow.model.repository.TransitionRuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransitionRuleService {
    private final TransitionRuleRepository repository;
    public TransitionRuleService(TransitionRuleRepository repository) { this.repository = repository; }
    public List<TransitionRule> getRules() { return repository.findAll(); }
    public Optional<TransitionRule> getRuleById(Long id) { return repository.findById(id); }
    public List<TransitionRule> getRulesByBoard(Long boardId) { return repository.findByBoard_Id(boardId); }
    @Transactional
    public TransitionRule salvar(TransitionRule entity) { return repository.save(entity); }
    @Transactional
    public void excluir(TransitionRule entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
