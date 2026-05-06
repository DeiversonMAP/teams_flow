package com.example.teamsFlow.model.api.dto;

import com.example.teamsFlow.model.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private ModelMapper modelMapper;

    public TaskDTO converterParaDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    public Task converterParaEntidade(TaskDTO dto) {
        return modelMapper.map(dto, Task.class);
    }
}