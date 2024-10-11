package uy.edu.um.wtf.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @ElementCollection
    @Column(name = "SEATS", nullable = false)
    @NotNull(message = "La lista de asientos no puede estar vacía (null)")
    @NotEmpty(message = "La lista de asientos no puede estar vacía")
    private List<Integer> seats;

    @Column(name = "PURCHASE_DATE", nullable = false)
    @Past(message = "La fecha no esta dentro de un rango valido")
    @NotNull (message = "La fecha de compra no puede estar vacía")
    private LocalDateTime purchaseDate;

    @Column(name = "TOTAL_PRICE", nullable = false)
    @NotNull (message = "El precio no puede estar vacía")
    @Min(value = 0, message = "El precio debe ser mayor o igual que 0")
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "MOVIE_SCREENING_ID", nullable = false)
    @NotNull (message = "La funcion no puede estar vacía")
    private MovieScreening movieScreening;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    @NotNull (message = "El cliente no puede estar vacía")
    private Client client;



}
