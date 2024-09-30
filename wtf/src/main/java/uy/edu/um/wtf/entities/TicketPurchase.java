package uy.edu.um.wtf.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TICKET_PURCHASE")

public class TicketPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SEAT", nullable = false)
    private int[] seat;

    @Column(name = "PURCHASE_DATE", nullable = false)
    private LocalDateTime purchaseDate;

    @Column(name = "TOTAL_PRICE", nullable = false)
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "MOVIE_SCREENING_ID", nullable = false)
    private MovieScreening movieScreening;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;



}
