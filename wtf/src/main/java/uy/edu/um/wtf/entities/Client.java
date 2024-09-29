package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@DiscriminatorValue("CLIENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Client extends User {

    @Column(name = "CARD_NUMBER", unique = true)
    private Long cardNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TicketPurchase> ticketPurchaseList = new LinkedList<>();

}
