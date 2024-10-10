package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "SCREEN")

public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    @NotBlank (message = "El nombre no puede estar vacío.")
    @NotNull (message = "El nombre no puede estar vacío.")
    private String name;

    @ManyToOne
    @JoinColumn(name = "CINEMA_ID", nullable = false)
    @NotNull (message = "El nombre del cine no puede estar vacío.")
    private Cinema cinema;

    @Column(name = "COLUMNS",nullable = false)
    @NotNull (message = "Dimensiones no validas")
    @Positive (message = "Dimensiones no validas")
    private Integer columms;

    @Column(name = "ROWS", nullable = false)
    @NotNull (message = "Dimensiones no validas")
    @Positive (message = "Dimensiones no validas")
    private Integer rows;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<MovieScreening> movieScreenings = new LinkedList<>();
}
