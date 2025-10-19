package com.kaiburr.taskapp.controllers;

import com.kaiburr.taskapp.model.Task;
import com.kaiburr.taskapp.model.TaskExecution;
import com.kaiburr.taskapp.service.K8sExecutorService;
import com.kaiburr.taskapp.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final K8sExecutorService k8sExecutor;

    public TaskController(TaskService taskService, K8sExecutorService k8sExecutor) {
        this.taskService = taskService;
        this.k8sExecutor = k8sExecutor;
    }

    // ✅ GET all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // ✅ GET task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable String id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ CREATE new task (Changed from PUT → POST)
    @PostMapping
        public ResponseEntity<?> createTask(@RequestBody Task task) {
            try {
                // Validate input
                if (task.getCommand() == null || task.getCommand().isBlank()) {
                    return ResponseEntity.badRequest().body("Command cannot be null or empty");
                }

                String cmd = task.getCommand().toLowerCase();
                if (cmd.contains("rm") || cmd.contains("sudo")) {
                    return ResponseEntity.badRequest().body("Unsafe command detected");
                }

                // Save task in database
                Task saved = taskService.createOrUpdate(task);
                return ResponseEntity.ok(saved);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Error creating task: " + e.getMessage());
            }
        }



    // ✅ UPDATE existing task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task updatedTask) {
        return taskService.getTaskById(id)
                .map(existing -> {
                    existing.setName(updatedTask.getName());
                    existing.setDescription(updatedTask.getDescription());
                    existing.setCommand(updatedTask.getCommand());
                    Task saved = taskService.createOrUpdate(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ SEARCH task by name
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchByName(@RequestParam String name) {
        List<Task> tasks = taskService.findByName(name);
        if (tasks.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tasks);
    }

    // ✅ EXECUTE a task (same as before)
    @PostMapping("/{id}/execute")
    public ResponseEntity<String> executeTask(@PathVariable String id) {
        Optional<Task> optional = taskService.getTaskById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Task task = optional.get();
        Date start = new Date();
        String output = k8sExecutor.executeCommand(task.getCommand());
        Date end = new Date();

        TaskExecution exec = new TaskExecution(start, end, output);
        task.getTaskExecutions().add(exec);
        taskService.createOrUpdate(task);

        return ResponseEntity.ok("Executed: " + output);
    }
}
