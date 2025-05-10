package ma.ensat.taskmanagement;

import ma.ensat.taskmanagement.Enum.TaskStatus;
import ma.ensat.taskmanagement.dto.TaskDTO;
import ma.ensat.taskmanagement.dto.TaskUpdateDTO;
import ma.ensat.taskmanagement.entity.Task;
import ma.ensat.taskmanagement.entity.User;
import ma.ensat.taskmanagement.repository.TaskRepository;
import ma.ensat.taskmanagement.repository.UserRepository;
import ma.ensat.taskmanagement.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void testUpdateTaskStatus_Success() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@gmail.com");

        Task task = new Task();
        task.setId(1);
        task.setStatut(TaskStatus.A_FAIRE);
        task.setTitre("Original title");
        task.setDescription("Original description");
        task.setUtilisateurAssigne(user);

        TaskUpdateDTO updateDTO = new TaskUpdateDTO(
                "Updated title",
                "Updated description",
                TaskStatus.EN_COURS
        );

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        SecurityContextHolder.setContext(securityContext);

        TaskDTO result = taskService.updateTaskStatus(1, updateDTO);

        assertAll(
                () -> assertEquals(TaskStatus.EN_COURS, result.getStatut()),
                () -> assertEquals("Updated title", result.getTitre()),
                () -> assertEquals("Updated description", result.getDescription()),
                () -> assertEquals(1, result.getUtilisateurAssigneId())
        );

        // 6. Verify interactions
        verify(taskRepository).findById(1);
        verify(taskRepository).save(task);
        verify(userRepository).findByEmail("test@gmail.com");
    }
}