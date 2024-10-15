package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.CreditCardNumber;

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
    @CreditCardNumber(message = "Tarjeta invalida")
    private String cardNumber;

    @Column(name = "CARD_DATE")
    @Past(message = "Tarjeta vencida")
    private LocalDate cardExpirationDate;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TicketPurchase> ticketPurchaseList = new LinkedList<>();

}
