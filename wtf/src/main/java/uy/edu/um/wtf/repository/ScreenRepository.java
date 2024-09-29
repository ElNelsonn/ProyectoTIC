package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.entities.Screen;
import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

    public List<Screen> findScreensByName(String name);

    public List<Screen> findScreensByCinema(Cinema cinemaName);



}
