package com.ntg.ntgapps.tasks.controller;

import com.ntg.ntgapps.tasks.JsonUtil;
import com.ntg.ntgapps.tasks.dto.TaskDto;
import com.ntg.ntgapps.tasks.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TaskController.class)
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskService service;

    @Test
    public void whenPostTask_thenCreateTask() throws Exception {
        TaskDto taskDto = new TaskDto("Task Title", "Task Description");
        given(service.createTask(Mockito.any())).willReturn(taskDto);

        mvc.perform(post("/v1/ntgapps").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(taskDto))).andExpect(status().isCreated()).andExpect(jsonPath("$.title", is("Task Title")));
        verify(service, VerificationModeFactory.times(1)).createTask(Mockito.any());
        reset(service);

    }

    @Test
    public void givenTasks_whenGetTasks_thenReturnJsonArray() throws Exception {

        TaskDto task1Dto = new TaskDto("Task1", "Task1");
        TaskDto task2Dto = new TaskDto("Task2", "Task2");
        TaskDto task3Dto = new TaskDto("Task3", "Task3");
        List<TaskDto> allTasks = Arrays.asList(task1Dto, task2Dto, task3Dto);

        given(service.findAllTasks()).willReturn(allTasks);

        mvc.perform(get("/v1/ntgapps").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[0].title", is(task1Dto.getTitle()))).andExpect(jsonPath("$[1].title", is(task2Dto.getTitle())))
                .andExpect(jsonPath("$[2].title", is(task3Dto.getTitle())));

        verify(service, VerificationModeFactory.times(1)).findAllTasks();
        reset(service);
    }

}
