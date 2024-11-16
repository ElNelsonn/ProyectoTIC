package uy.edu.um.wtf.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatForPurchase {


    private String seatsRowCol;

    private Boolean isOccupied;


}
