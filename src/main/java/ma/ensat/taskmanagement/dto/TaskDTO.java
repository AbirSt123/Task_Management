package ma.ensat.taskmanagement.dto;


import lombok.Data;
import ma.ensat.taskmanagement.Enum.TaskStatus;

@Data
public class TaskDTO {
    private Integer id;
    private String titre;
    private String description;
    private TaskStatus statut;
    private Integer utilisateurAssigneId;

}
