package ma.ensat.taskmanagement.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.ensat.taskmanagement.Enum.TaskStatus;
import ma.ensat.taskmanagement.Exception.TaskNotFoundException;
import ma.ensat.taskmanagement.Exception.UnauthorizedTaskUpdateException;
import ma.ensat.taskmanagement.dto.TaskCreateDTO;
import ma.ensat.taskmanagement.dto.TaskDTO;
import ma.ensat.taskmanagement.dto.TaskUpdateDTO;
import ma.ensat.taskmanagement.entity.Task;
import ma.ensat.taskmanagement.entity.User;
import ma.ensat.taskmanagement.repository.TaskRepository;
import ma.ensat.taskmanagement.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskDTO createTask(TaskCreateDTO taskCreateDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Task task = Task.builder()
                .titre(taskCreateDTO.getTitre())
                .description(taskCreateDTO.getDescription())
                .statut(taskCreateDTO.getStatut())
                .utilisateurAssigne(currentUser)
                .build();

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }


    public List<TaskDTO> getTasksForCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return taskRepository.findByUtilisateurAssigne(user)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO updateTaskStatus(Integer id, TaskUpdateDTO taskUpdateDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!task.getUtilisateurAssigne().getId().equals(currentUser.getId())) {
            throw new UnauthorizedTaskUpdateException("You can only update tasks assigned to you");
        }

        task.setTitre(taskUpdateDTO.getTitre());
        task.setDescription(taskUpdateDTO.getDescription());
        task.setStatut(taskUpdateDTO.getStatut());

        Task updatedTask = taskRepository.save(task);
        return convertToDTO(updatedTask);
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitre(task.getTitre());
        dto.setDescription(task.getDescription());
        dto.setStatut(task.getStatut());
        dto.setUtilisateurAssigneId(task.getUtilisateurAssigne().getId());
        return dto;
    }


}
