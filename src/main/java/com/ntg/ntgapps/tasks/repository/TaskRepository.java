package com.ntg.ntgapps.tasks.repository;


import com.ntg.ntgapps.tasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    public Task findByTitle(String title);

    public Task findByDescription(String description);
}
