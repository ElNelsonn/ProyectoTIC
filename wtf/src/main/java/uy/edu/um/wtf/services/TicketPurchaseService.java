package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.repository.TicketPurchaseRepository;

@Service
public class TicketPurchaseService {

    @Autowired
    private TicketPurchaseRepository ticketRepo;


}
