package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.entities.Screen;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

    public List<Screen> findScreensByName(String name);

    public Optional<Screen> findScreenByNameAndCinema(String screenName, Cinema cinema);

    public List<Screen> findScreensByCinema(Cinema cinemaName);





}
