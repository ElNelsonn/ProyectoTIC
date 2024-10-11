package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.TicketPurchase;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.*;
import java.time.LocalDateTime;
import java.util.Optional;
import uy.edu.um.wtf.entities.TicketPurchase;
import uy.edu.um.wtf.repository.TicketPurchaseRepository;


import java.util.List;

@Service
public class TicketPurchaseService {

    @Autowired
    private TicketPurchaseRepository ticketRepo;

    @Autowired
    private ClientRepository clientRepo;



}