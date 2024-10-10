package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "MOVIE_SCREENING")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MovieScreening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE", nullable = false)
    @NotNull(message = "fecha no valida.")
    @Past(message = "fecha no valida.")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    @NotNull(message = "La pelicula no es valida.")
    //@NotBlank(message = "La pelicula no es valida.")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "SCREEN_ID", nullable = false)
    @NotNull(message = "La Screen no es valida.")
    private Screen screen;

    @Column(name = "SEATS", nullable = false)
    private boolean[] seats;

    @OneToMany(mappedBy = "movieScreening", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TicketPurchase> ticketPurchaseList = new LinkedList<>();















}
