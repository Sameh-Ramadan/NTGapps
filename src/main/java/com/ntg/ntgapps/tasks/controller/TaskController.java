package com.ntg.ntgapps.tasks.controller;

import com.ntg.ntgapps.tasks.dto.TaskDto;
import com.ntg.ntgapps.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/ntgapps")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService service) {
        this.taskService = service;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        HttpStatus status = HttpStatus.CREATED;
        TaskDto dto = taskService.createTask(taskDto);
        return  new ResponseEntity<TaskDto>(dto, status);
    }

    @GetMapping()
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.findAllTasks();
        return ResponseEntity.ok(tasks);
    }

}
