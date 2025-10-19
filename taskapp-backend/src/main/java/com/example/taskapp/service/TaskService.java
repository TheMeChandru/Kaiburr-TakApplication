package com.kaiburr.taskapp.service;

import com.kaiburr.taskapp.model.Task;
import com.kaiburr.taskapp.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return repo.findById(id);
    }

    public Task createOrUpdate(Task task) {
        return repo.save(task);
    }

    public void deleteTask(String id) {
        repo.deleteById(id);
    }

    public List<Task> findByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }
}
