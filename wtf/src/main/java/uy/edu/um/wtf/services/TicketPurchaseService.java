package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.TicketPurchase;
import uy.edu.um.wtf.repository.TicketPurchaseRepository;

import java.util.List;

@Service
public class TicketPurchaseService {

    @Autowired
    private TicketPurchaseRepository ticketRepo;

    public List<TicketPurchase> allTicketPurchases(){return ticketRepo.findAll();}

}
