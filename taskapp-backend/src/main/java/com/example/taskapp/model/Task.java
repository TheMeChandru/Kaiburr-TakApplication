package com.kaiburr.taskapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String name;
    private String description;
    private String command;
    private List<TaskExecution> taskExecutions = new ArrayList<>();

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }

    public List<TaskExecution> getTaskExecutions() { return taskExecutions; }
    public void setTaskExecutions(List<TaskExecution> taskExecutions) { this.taskExecutions = taskExecutions; }
}
