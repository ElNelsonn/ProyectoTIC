package uy.edu.um.wtf.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "SNACK_PURCHASE")
public class SnackPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PURCHASED_SNACKS", joinColumns = @JoinColumn(name = "SNACK_PURCHASE_ID"), inverseJoinColumns = @JoinColumn(name = "SNACK_ID"))
    private List<Snack> snackList;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;

    @Column(name = "TOTAL_PRICE")
    private Long totalPrice;

}
