package com.example.teamsFlow.api.dto;

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