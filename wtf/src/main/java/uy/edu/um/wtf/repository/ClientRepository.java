package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    public List<Client> findClientsByName(String name);

    public Optional<Client> findClientByIdentityCard(Long identityCard);

    public List<Client> findClientsBySurname(String surname);

    public Optional<Client> findClientByCardNumber(Long cardNumber);


}