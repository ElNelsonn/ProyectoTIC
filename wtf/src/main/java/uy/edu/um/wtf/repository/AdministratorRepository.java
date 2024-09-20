package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.wtf.entities.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator,Long> {
}
