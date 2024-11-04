package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.entities.SnackPurchase;

import java.util.List;

@Repository
public interface SnackPurchaseRepository extends JpaRepository<SnackPurchase, Long> {

    List<SnackPurchase> findAllByClient_Email(String mail);
}
