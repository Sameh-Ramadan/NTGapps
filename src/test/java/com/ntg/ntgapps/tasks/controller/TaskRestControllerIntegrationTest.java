package com.ntg.ntgapps.tasks.controller;

import com.ntg.ntgapps.NTGAppsApplication;
import com.ntg.ntgapps.tasks.JsonUtil;
import com.ntg.ntgapps.tasks.dto.TaskDto;
import com.ntg.ntgapps.tasks.model.Task;
import com.ntg.ntgapps.tasks.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = NTGAppsApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class TaskRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateTask() throws IOException, Exception {
        TaskDto taskDto = new TaskDto("Task1", "Task1");
        mvc.perform(post("/v1/ntgapps").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(taskDto))).andDo(print());

        List<Task> found = repository.findAll();
        assertThat(found).extracting(Task::getTitle).containsOnly("Task1");
    }

    @Test
    public void givenTasks_whenGetTasks_thenStatus200() throws Exception {
        createTestTask("Task2", "Task2");
        createTestTask("Task3", "Task3");

        mvc.perform(get("/v1/ntgapps").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].title", is("Task2")))
                .andExpect(jsonPath("$[1].title", is("Task3")));
    }

    private void createTestTask(String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        repository.saveAndFlush(task);
    }

}
