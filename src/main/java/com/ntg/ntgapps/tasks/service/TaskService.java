package com.ntg.ntgapps.tasks.service;

import com.ntg.ntgapps.tasks.dto.TaskDto;
import com.ntg.ntgapps.tasks.model.Task;
import com.ntg.ntgapps.tasks.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskDto createTask(TaskDto taskDto) {

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());

        Task savedTask = taskRepository.save(task);
        return new TaskDto(savedTask.getTitle(), savedTask.getDescription());
    }

    public List<TaskDto> findAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> dtos = new ArrayList<>();

        for (Task task : tasks) {
            TaskDto dto = new TaskDto(task.getTitle(), task.getDescription());
            dtos.add(dto);
        }

        return dtos;
    }


    public TaskDto getTaskByTitle(String title) {
        Task task =  taskRepository.findByTitle(title);
        TaskDto dto = null;

        if (task != null){
            dto = new TaskDto(task.getTitle(), task.getDescription() );
        }
        return dto;
    }


    public TaskDto getTaskByDescription(String description) {
        Task task =  taskRepository.findByDescription(description);
        TaskDto dto = new TaskDto(task.getTitle(), task.getDescription());
        return dto;
    }

}
