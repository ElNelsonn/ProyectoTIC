package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Administrator;
import java.util.List;
import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator,Long> {

    public List<Administrator> findAdministratorsByName(String name);

    public Optional<Administrator> findAdministratorByIdentityCard(Long identityCard);

    public List<Administrator> findAdministratorsBySurname(String surname);

}
