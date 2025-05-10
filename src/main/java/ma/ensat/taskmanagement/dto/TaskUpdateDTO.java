package ma.ensat.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ma.ensat.taskmanagement.Enum.TaskStatus;

@Data
public class TaskUpdateDTO {
    @NotBlank(message = "Title is required")
    private String titre;

    private String description;
    private TaskStatus statut;

    public TaskUpdateDTO(String titre, String description, TaskStatus statut) {
        this.titre = titre;
        this.description = description;
        this.statut = statut;
    }
}
