package com.ntg.ntgapps.tasks.service;

import com.ntg.ntgapps.tasks.dto.TaskDto;
import com.ntg.ntgapps.tasks.model.Task;
import com.ntg.ntgapps.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class TaskServiceIntegrationTest {

    @TestConfiguration
    static class TaskServiceIntegrationTestContextConfiguration {
        @Bean
        public TaskService taskService() {
            return new TaskService();
        }
    }

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        Task task1 = new Task();
        task1.setTitle("Task1");
        task1.setDescription("Task1");

        Task task2 = new Task();
        task2.setTitle("Task2");
        task2.setDescription("Task2");

        Task task3 = new Task();
        task3.setTitle("Task3");
        task3.setDescription("Task3");

        List<Task> allTasks = Arrays.asList(task1, task2, task3);

        Mockito.when(taskRepository.findByTitle(task1.getTitle())).thenReturn(task1);
        Mockito.when(taskRepository.findByTitle(task2.getTitle())).thenReturn(task2);
        Mockito.when(taskRepository.findByTitle("wrong_name")).thenReturn(null);
        Mockito.when(taskRepository.findAll()).thenReturn(allTasks);

        Mockito.when(taskRepository.findByDescription(task1.getDescription())).thenReturn(task1);
        Mockito.when(taskRepository.findByDescription("")).thenReturn(null);
    }

    @Test
    public void whenValidTitle_thenTaskShouldBeFound() {
        String title = "Task1";
        TaskDto found = taskService.getTaskByTitle(title);
        assertThat(found.getTitle()).isEqualTo(title);
    }

    @Test
    public void whenInValidTitle_thenTaskShouldNotBeFound() {
        TaskDto fromDb = taskService.getTaskByTitle("wrong_name");
        assertThat(fromDb).isNull();
    }

    @Test
    public void given3Tasks_whengetAll_thenReturn3Records() {

        TaskDto task1 = new TaskDto("Task1", "Task1");
        TaskDto task2 = new TaskDto("Task2", "Task2");
        TaskDto task3 = new TaskDto("Task3", "Task3");

        List<TaskDto> allTasks = taskService.findAllTasks();
        assertThat(allTasks).hasSize(3).extracting(TaskDto::getTitle).contains(task1.getTitle(), task2.getTitle(), task3.getTitle());

    }
}
