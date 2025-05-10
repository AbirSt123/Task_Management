package ma.ensat.taskmanagement;

import ma.ensat.taskmanagement.Enum.TaskStatus;
import ma.ensat.taskmanagement.dto.TaskDTO;
import ma.ensat.taskmanagement.dto.TaskUpdateDTO;
import ma.ensat.taskmanagement.entity.Task;
import ma.ensat.taskmanagement.entity.User;
import ma.ensat.taskmanagement.repository.TaskRepository;
import ma.ensat.taskmanagement.repository.UserRepository;
import ma.ensat.taskmanagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static ma.ensat.taskmanagement.Enum.TaskStatus.*;
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
    public void testUpdateTaskStatus() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setId(1);

        Task task = new Task();
        task.setId(1);
        task.setStatut(TaskStatus.A_FAIRE);
        task.setTitre("Original title");
        task.setDescription("Original description");
        task.setUtilisateurAssigne(user);

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        SecurityContextHolder.setContext(securityContext);

        TaskUpdateDTO updateDTO = new TaskUpdateDTO("Updated title", "Updated description", TaskStatus.EN_COURS);

        TaskDTO updatedTaskDTO = taskService.updateTaskStatus(1, updateDTO);

        assertEquals(TaskStatus.EN_COURS, updatedTaskDTO.getStatut());
        assertEquals("Updated title", updatedTaskDTO.getTitre());
        assertEquals("Updated description", updatedTaskDTO.getDescription());
        assertEquals(1, updatedTaskDTO.getUtilisateurAssigneId());

        verify(taskRepository).findById(1);
        verify(taskRepository).save(task);
        verify(userRepository).findByEmail("test@gmail.com");
    }
    }
