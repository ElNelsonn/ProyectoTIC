package uy.edu.um.wtf.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("ADMINISTRATOR")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Administrator extends User {

}
