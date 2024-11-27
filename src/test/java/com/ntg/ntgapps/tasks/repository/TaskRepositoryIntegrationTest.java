package com.ntg.ntgapps.tasks.repository;

import com.ntg.ntgapps.tasks.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TaskRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void whenFindByTitleAndDescription_thenReturnTask() {
        Task task1 = new Task();
        task1.setTitle("Task1");
        task1.setDescription("Task1");
        entityManager.persistAndFlush(task1);

        Task found = taskRepository.findByTitle(task1.getTitle());
        assertThat(((Task) found).getTitle()).isEqualTo(task1.getTitle());

        Task found2 = taskRepository.findByDescription(task1.getDescription());
        assertThat(((Task) found2).getDescription()).isEqualTo(task1.getDescription());
    }

    @Test
    public void whenInvalidTitle_thenReturnNull() {
        Task fromDb = taskRepository.findByTitle("doesNotExist");
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfTasks_whenFindAll_thenReturnAllTasks() {

        Task task1 = new Task();
        task1.setTitle("Task1");
        task1.setDescription("Task1");

        Task task2 = new Task();
        task2.setTitle("Task2");
        task2.setDescription("Task2");

        Task task3 = new Task();
        task3.setTitle("Task3");
        task3.setDescription("Task3");

        entityManager.persist(task1);
        entityManager.persist(task2);
        entityManager.persist(task3);
        entityManager.flush();

        List<Task> allTasks = taskRepository.findAll();

        assertThat(allTasks).hasSize(3).extracting(Task::getTitle).containsOnly(task1.getTitle(), task2.getTitle(), task3.getTitle());
    }


}
