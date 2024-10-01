package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.edu.um.wtf.entities.SnackPurchase;

@Repository
public interface SnackPurchaseRepository extends JpaRepository<SnackPurchase, Long> {



}
