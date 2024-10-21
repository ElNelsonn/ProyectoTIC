package uy.edu.um.wtf.repository;

import org.springframework.stereotype.Repository;
import uy.edu.um.wtf.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findUserByIdentityCard(Long identityCard);

    public Optional<User> findUserByEmail(String email);


}
