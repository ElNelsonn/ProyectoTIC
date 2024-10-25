package uy.edu.um.wtf.DTO;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieForController {

    @Column(name = "TITLE", nullable = false, unique = true)
    @NotBlank(message = "El titulo no puede ser vacío.")
    @NotNull(message = "El titulo no puede ser vacío.")
    @Size(min = 2, max = 30, message = "El nombre de la pelicula debe tener entre 2 y 30 caracteres")
    private String title;

    @Column(name = "RELEASE_DATE", nullable = false)
    @NotNull (message = "La fecha de lanzamiento no es valida.")
    @Past(message = "La fecha de lanzamiento no es valida.")
    private LocalDate releaseDate;

    @Column(name = "DIRECTORS", nullable = false)
    @NotNull (message = "La pelicula debe tener algun director")
    @Size(min = 2, message = "La pelicula debe tener algun director")
    private String directors;

    @Column(name = "SYNOPSIS")
    private String synopsis;

    @Column(name = "CATEGORIES", nullable = false)
    @NotNull (message = "Las categorías no pueden estar vacía.")
    @Size(min = 2, message = "Las categorías no pueden estar vacía.")
    private String categories;


    @Column(name = "ACTORS", nullable = false)
    @NotNull (message = "Es necesario poner minimo un autor")
    @Size(min = 2, message = "Es necesario poner minimo un autor")
    private String actors;

    @Column(name = "DURATION", nullable = false)
    @NotNull (message = "Duración de pelicula invalida.")
    @Min(value = 1, message = "La duracion debe ser mayor que 0")
    private Long duration;

    @Column(name = "CLASSIFICATION", nullable = false)
    @NotBlank(message = "La clasificación no puede estar vacía")
    @NotNull(message = "La clasificación no puede estar vacía")
    private String classification;

    @Column(name = "POSTER")
    @NotNull(message = "El poster no puede estar vacío.")
    @URL(message = "URL no valida.")
    private String posterURL;



}
