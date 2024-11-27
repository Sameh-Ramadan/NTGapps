package com.ntg.ntgapps.tasks.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskDto {

    private String title;
    private String description;

    public TaskDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
