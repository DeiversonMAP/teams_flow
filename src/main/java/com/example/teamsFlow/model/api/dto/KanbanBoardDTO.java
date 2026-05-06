package com.example.teamsFlow.model.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KanbanBoardDTO {
    private Long id;
    private Long projectId;
}