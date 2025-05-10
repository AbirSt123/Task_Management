package ma.ensat.taskmanagement.repository;

import ma.ensat.taskmanagement.entity.Task;
import ma.ensat.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUtilisateurAssigne(User user);
}
