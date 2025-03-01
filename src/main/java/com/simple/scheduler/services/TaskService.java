package com.simple.scheduler.services;

import com.simple.scheduler.dtos.CreateTaskRequest;
import com.simple.scheduler.dtos.TaskDto;
import com.simple.scheduler.dtos.TaskSummaryResponse;
import com.simple.scheduler.dtos.UpdateTaskRequest;
import com.simple.scheduler.models.Task;
import com.simple.scheduler.models.User;
import com.simple.scheduler.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public List<TaskDto> getAllTasks(User user) {
        List<Task> allByUser = taskRepository.findAllByUser(user);
        List<TaskDto> taskDtos = new ArrayList<>();

        for (Task task : allByUser) {
            taskDtos.add(
                    TaskToDto(task)
            );
        }
        return taskDtos;
    }

    public TaskDto createTask(CreateTaskRequest request, User user) {
        Task task = Task.builder()
                .title(request.getTitle())
                .text(request.getText())
                .isCompleted(false)
                .user(user)
                .build();
        return TaskToDto(taskRepository.save(task));
    }

    public void deleteTask(Long taskId, User user) {
        verifyUserPermission(taskId,user);
        taskRepository.deleteById(taskId);
    }

    public void updateTask(UpdateTaskRequest request, User user) {
        Task task = verifyUserPermission(request.getTaskId(), user);
        task.setTitle(request.getTitle());
        task.setText(request.getText());
        taskRepository.save(task);
    }

    public TaskDto TaskToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .text(task.getText())
                .completedAt(task.getCompletedAt())
                .isCompleted(task.getIsCompleted())
                .user(userService.userToUserDto(task.getUser()))
                .build();
    }

    private Task verifyUserPermission(Long taskId, User user) {
        Optional<Task> byId = taskRepository.findById(taskId);
        byId.ifPresent(t -> {
            if (!t.getUser().equals(user)) {
                throw new AccessDeniedException("You don't have permission to delete this task");
            }
        });
        return byId.get();
    }

    public void completeTask(Long taskId, User user) {
        Task task = verifyUserPermission(taskId, user);
        task.setIsCompleted(true);
        task.setCompletedAt(new Timestamp(System.currentTimeMillis()));
        taskRepository.save(task);
    }

    public void uncompleteTask(Long taskId, User user) {
        Task task = verifyUserPermission(taskId, user);
        task.setIsCompleted(false);
        task.setCompletedAt(null);
        taskRepository.save(task);
    }

    public TaskSummaryResponse getTaskSummary(User user) {
        long completedTasks = taskRepository.countAllCompletedTasksByUser(user.getId());
        long uncompletedTasks = taskRepository.getAllUncompletedTasksByUser(user.getId());
        return new TaskSummaryResponse(completedTasks, uncompletedTasks);
    }
}
