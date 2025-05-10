package ma.ensat.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ma.ensat.taskmanagement.Enum.TaskStatus;

@Data
public class TaskCreateDTO {
    @NotBlank(message = "Title is required")
    private String titre;

    private String description;

    private TaskStatus statut = TaskStatus.A_FAIRE;
    private Integer utilisateurAssigneId;
}
