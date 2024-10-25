package uy.edu.um.wtf.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SnackForController {

    private Integer id;
    private String nombre;
    private Long precio;
    private String imagen;

}
