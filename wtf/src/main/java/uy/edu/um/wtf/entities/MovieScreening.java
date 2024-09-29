package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
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
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "SCREEN_ID", nullable = false)
    private Screen screen;

    @Column(name = "SEATS", nullable = false)
    private boolean[] seats;

    @OneToMany(mappedBy = "movieScreening", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TicketPurchase> ticketPurchaseList = new LinkedList<>();















}
