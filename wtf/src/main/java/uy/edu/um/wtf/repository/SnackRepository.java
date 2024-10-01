package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.edu.um.wtf.entities.Snack;

import java.util.Optional;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Long> {

    public Optional<Snack> findSnackByName(String name);



}
