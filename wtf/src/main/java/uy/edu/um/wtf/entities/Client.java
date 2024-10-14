package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@DiscriminatorValue("CLIENT")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Client extends User {

    @Column(name = "CARD_NUMBER", unique = true)
    private Long cardNumber;

    @Column(name = "CARD_DATE")
    private LocalDate card

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TicketPurchase> ticketPurchaseList = new LinkedList<>();

}
