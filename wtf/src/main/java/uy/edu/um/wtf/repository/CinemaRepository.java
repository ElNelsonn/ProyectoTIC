package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Cinema;
import java.util.Optional;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    public Optional<Cinema> findCinemaByName(String name);


}
