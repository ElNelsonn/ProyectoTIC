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
@Table(name = "SNACK_PURCHASE")
public class SnackPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PURCHASED_SNACKS", joinColumns = @JoinColumn(name = "SNACK_PURCHASE_ID"), inverseJoinColumns = @JoinColumn(name = "SNACK_ID"))
    @NotNull(message = "La lista de snacks no puedo estar vacía. (null)")
    @NotEmpty(message = "La lista de snacks no puede estar vacía")
    private List<Snack> snackList;

    @Column(name = "DATE", nullable = false)
    @NotNull (message = "La fecha no puede estar vacia.")
    @Past (message = "La fecha no es valida.")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    @NotNull(message = "El cliente no es valido.")
    private Client client;

    @Column(name = "TOTAL_PRICE")
    @NotNull(message = "El precio no puede estar vacía")
    @Min(value = 0, message = "El precio debe ser mayor o igual que 0")
    private Long totalPrice;

}
