package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.edu.um.wtf.entities.Cinema;
import java.util.List;
import java.util.Optional;


@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    public Optional<Cinema> findCinemaByName(String name);

    public Optional<Cinema> findCinemaByEmail(String mail);

    public List<Cinema> findCinemasByLocation(String location);






}
