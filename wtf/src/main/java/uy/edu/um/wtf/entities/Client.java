package uy.edu.um.wtf.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "CLIENT")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client extends User {

    @Column(name = "CARD_NUMBER")
    private Long cardNumber;

}
