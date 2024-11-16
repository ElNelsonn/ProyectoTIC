package uy.edu.um.wtf.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.NamedEntityGraph;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Seat {


    @Column(name = "SEAT_NUMBER", nullable = false)
    private Integer seatNumber;

    @Column(name = "IS_OCCUPIED", nullable = false)
    private Boolean isOccupied;


}
