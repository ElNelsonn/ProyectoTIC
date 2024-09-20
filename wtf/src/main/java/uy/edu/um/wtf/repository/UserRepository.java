package uy.edu.um.wtf.repository;
import uy.edu.um.wtf.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findByName(String Name);

}
