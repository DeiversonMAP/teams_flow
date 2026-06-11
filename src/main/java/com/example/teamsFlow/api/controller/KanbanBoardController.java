package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.KanbanBoardDTO;
import com.example.teamsFlow.model.entity.KanbanBoard;
import com.example.teamsFlow.model.repository.KanbanBoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kanban")
public class KanbanBoardController {

    @Autowired
    private KanbanBoardRepository kanbanBoardRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Buscar o Kanban Board pelo ID do Projeto
    @GetMapping("/project/{projectId}")
    public ResponseEntity<KanbanBoardDTO> getBoardByProject(@PathVariable Long projectId) {
        return kanbanBoardRepository.findByProjectId(projectId)
                .map(board -> ResponseEntity.ok(modelMapper.map(board, KanbanBoardDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<KanbanBoardDTO> createBoard(@RequestBody KanbanBoardDTO boardDTO) {
        KanbanBoard board = modelMapper.map(boardDTO, KanbanBoard.class);
        KanbanBoard savedBoard = kanbanBoardRepository.save(board);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(savedBoard, KanbanBoardDTO.class));
    }
}