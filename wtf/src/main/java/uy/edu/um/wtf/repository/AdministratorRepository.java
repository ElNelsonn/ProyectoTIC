package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.edu.um.wtf.entities.Administrator;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Long> {

    public List<Administrator> findAll();

    public List<Administrator> findAdministratorsByName(String name);

    public Optional<Administrator> findAdministratorByIdentityCard(Long identityCard);

    public List<Administrator> findAdministratorsBySurname(String surname);

}
