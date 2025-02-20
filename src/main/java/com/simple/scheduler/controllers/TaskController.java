package com.simple.scheduler.controllers;

import com.simple.scheduler.dtos.*;
import com.simple.scheduler.models.User;
import com.simple.scheduler.services.TaskService;
import com.simple.scheduler.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public TaskController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks(
            @RequestHeader(name = "Authorization") String token
    ) {
        User userByToken = userService.getUserByToken(token);

        return ResponseEntity.ok().body(
                taskService.getAllTasks(userByToken)
        );
    }

    @PostMapping("/tasks/create")
    public ResponseEntity<?> createTask(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody CreateTaskRequest request
    ) {
        User userByToken = userService.getUserByToken(token);
        taskService.createTask(request, userByToken);
        return ResponseEntity.ok().body(
                new Message("Task created successfully")
        );
    }

    @PostMapping("/tasks/delete")
    public ResponseEntity<?> deleteTask(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody DeleteTaskRequest request
    ) {
        User userByToken = userService.getUserByToken(token);
        taskService.deleteTask(request.getTaskId(), userByToken);
        return ResponseEntity.ok().body(
                new Message("Task deleted successfully")
        );
    }

    @PostMapping("/tasks/update")
    public ResponseEntity<?> updateTask(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody UpdateTaskRequest request
    ) {
        User userByToken = userService.getUserByToken(token);
        taskService.updateTask(request,userByToken);
        return ResponseEntity.ok().body(new Message("Task updated successfully"));
    }

    @PostMapping("/tasks/complete")
    public ResponseEntity<?> completeTask(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody CompleteTaskRequest request
    ) {
        User userByToken = userService.getUserByToken(token);
        taskService.completeTask(request.getTaskId(), userByToken);
        return ResponseEntity.ok().body(new Message("Task completed successfully"));
    }

    @PostMapping("/tasks/uncomplete")
    public ResponseEntity<?> uncompleteTask(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody UncompleteTaskRequest request
    ) {
        User userByToken = userService.getUserByToken(token);
        taskService.uncompleteTask(request.getTaskId(), userByToken);
        return ResponseEntity.ok().body(new Message("Task uncompleted successfully"));
    }

    @GetMapping("tasks/summary")
    public ResponseEntity<?> getTaskSummary(
            @RequestHeader(name = "Authorization") String token
    ) {
        User userByToken = userService.getUserByToken(token);
        TaskSummaryResponse response = taskService.getTaskSummary(userByToken);
        return ResponseEntity.ok().body(response);
    }
}
