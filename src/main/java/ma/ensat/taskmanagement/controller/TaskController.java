package ma.ensat.taskmanagement.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.ensat.taskmanagement.dto.TaskCreateDTO;
import ma.ensat.taskmanagement.dto.TaskDTO;
import ma.ensat.taskmanagement.dto.TaskUpdateDTO;
import ma.ensat.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task management endpoints")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Create Task", description = "Create a new task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskCreateDTO taskCreateDTO) {
        return ResponseEntity.ok(taskService.createTask(taskCreateDTO));
    }

    @GetMapping("/my-tasks")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "My Tasks", description = "Get tasks assigned to the current user")
    public ResponseEntity<List<TaskDTO>> getMyTasks() {
        return ResponseEntity.ok(taskService.getTasksForCurrentUser());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "All Tasks", description = "Get all tasks (admin only)")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Update Task Status", description = "Update the status of a task only for the assigned user")
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @PathVariable Integer id,
            @RequestBody TaskUpdateDTO taskUpdateDTO) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, taskUpdateDTO));
    }

}
