package uy.edu.um.wtf.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.edu.um.wtf.entities.Cinema;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenForController {

    @NotBlank(message = "El nombre no puede estar vacío.")
    @NotNull(message = "El nombre no puede estar vacío.")
    @Size(min = 1, max = 8, message = "El nombre debe tener entre 1 y 8 caracteres")
    private String name;

    @NotNull(message = "El cine no puede estar vacío.")
    @Size(min = 1, max = 8, message = "El nombre del cine debe tener entre 2 y 30 caracteres")
    private String cinemaName;

    @NotNull(message = "Dimensiones no validas")
    @Positive(message = "Dimensiones no validas")
    private Integer columns;

    @NotNull(message = "Dimensiones no validas")
    @Positive(message = "Dimensiones no validas")
    private Integer rows;





}
