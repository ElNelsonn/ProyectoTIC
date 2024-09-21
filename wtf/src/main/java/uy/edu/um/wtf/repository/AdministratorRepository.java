package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Administrator;
import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator,Long> {

    public Optional<Administrator> findAdministratorByName(String name);

    public Optional<Administrator> findAdministratorByIdentityCard(Long identityCard);

    public Optional<Administrator> findAdministratorBySurname(String surname);

}
