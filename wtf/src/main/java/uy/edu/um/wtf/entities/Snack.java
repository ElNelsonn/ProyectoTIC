package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "SNACK")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Snack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    @NotBlank(message = "El nombre del snack no puedo estar vacío. (null)")
    @NotNull(message = "El nombre del snack no puedo estar vacío.")
    @Size(min = 2, max = 30, message = "El nombre del snack debe tener entre 2 y 30 caracteres")
    private String name;

    @Column(name = "GLUTEN_FREE", nullable = false)
    @NotNull(message = "Debe indicarse si es gluten free.")
    private Boolean glutenFree;

    @Column(name = "PRICE", nullable = false)
    @NotNull(message = "Debe tener un precio asignado.")
    @Min(value = 0, message = "El precio debe ser mayor o igual que 0")
    private Long price;

}
