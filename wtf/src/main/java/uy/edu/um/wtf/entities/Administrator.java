package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ADMINISTRATOR")
@DiscriminatorValue("ADMINISTRATOR")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Administrator extends User {

}
