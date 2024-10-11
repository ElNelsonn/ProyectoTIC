package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "MOVIE")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", nullable = false, unique = true)
    @NotBlank(message = "El titulo no puede ser vacío.")
    @NotNull(message = "El titulo no puede ser vacío.")
    @Size(min = 2, max = 30, message = "El nombre de la pelicula debe tener entre 2 y 30 caracteres")
    private String title;

    @Column(name = "RELEASE_DATE", nullable = false)
    @NotNull (message = "La fecha de lanzamiento no es valida.")
    @Past (message = "La fecha de lanzamiento no es valida.")
    private LocalDate releaseDate;

    @ElementCollection
    @CollectionTable(name = "MOVIE_DIRECTORS",joinColumns = @JoinColumn(name="MOVIE_ID"))
    @Column(name = "DIRECTORS", nullable = false)
    @NotNull (message = "La pelicula debe tener algun director")
    @Size(min = 1, message = "Debe haber al menos un director.")
    private List<String> directors;

    @Column(name = "SYNOPSIS")
    private String synopsis;

    @ElementCollection
    @CollectionTable(name = "MOVIE_CATEGORIES",joinColumns=@JoinColumn(name="MOVIE_ID"))
    @Column(name = "CATEGORIES", nullable = false)
    @NotNull (message = "Las categorías no pueden estar vacía.")
    @Size(min = 1, message = "Debe haber al menos una categoría.")
    private List<String> categories;

    @ElementCollection
    @CollectionTable(name = "MOVIE_ACTORS",joinColumns = @JoinColumn(name="MOVIE_ID"))
    @Column(name = "ACTORS", nullable = false)
    @NotNull (message = "Es necesario poner los autores")
    @Size(min = 1, message = "Debe haber al menos un actor.")
    private List<String> actors;

    @Column(name = "DURATION", nullable = false)
    @NotNull (message = "Duración de pelicula invalida.")
    @Min(value = 1, message = "La duracion debe ser mayor que 0")
    private Long duration;

    @Column(name = "CLASSIFICATION", nullable = false)
    @NotBlank(message = "La clasificación no puede estar vacía")
    @NotNull(message = "La clasificación no puede estar vacía")
    private String classification;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<MovieScreening> movieScreenings = new LinkedList<>();

}
