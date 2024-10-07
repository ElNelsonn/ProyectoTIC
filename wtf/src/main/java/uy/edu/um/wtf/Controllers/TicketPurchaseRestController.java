package uy.edu.um.wtf.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.TicketPurchase;
import uy.edu.um.wtf.services.TicketPurchaseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/TicketPurchase")
public class TicketPurchaseRestController {

    @Autowired
    private TicketPurchaseService ticketPurchaseService;

    @RequestMapping("/")
    public ResponseEntity<List<TicketPurchase>> getAll(){return ResponseEntity.ok(ticketPurchaseService.allTicketPurchases());}
}
