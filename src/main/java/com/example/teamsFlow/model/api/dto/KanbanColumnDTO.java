package com.example.teamsFlow.model.api.dto;

import com.example.teamsFlow.model.enuns.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KanbanColumnDTO {
    private Long id;
    private String name;
    private Integer position;
    private TaskStatus status; // Enum
    private Long boardId;
}