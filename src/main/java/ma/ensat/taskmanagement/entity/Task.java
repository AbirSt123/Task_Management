package ma.ensat.taskmanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensat.taskmanagement.Enum.TaskStatus;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titre;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus statut;

    @ManyToOne
    private User utilisateurAssigne;

}
