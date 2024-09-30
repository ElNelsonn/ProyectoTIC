package uy.edu.um.wtf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.MovieScreening;
import uy.edu.um.wtf.entities.TicketPurchase;
import java.util.List;

@Repository
public interface TicketPurchaseRepository extends JpaRepository<TicketPurchase, Long> {

    public List<TicketPurchase> findTicketPurchasesByMovieScreening(MovieScreening movieScreening);

    public List<TicketPurchase> findTicketPurchasesBy(Client client);


}
