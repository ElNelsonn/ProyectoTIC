package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Client;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    public Optional<Client> findClientByName(String name);

    public Optional<Client> findClientByIdentityCard(Long identityCard);

    public Optional<Client> findClientBySurname(String surname);


}
