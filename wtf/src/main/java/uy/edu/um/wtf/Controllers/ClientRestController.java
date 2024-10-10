package uy.edu.um.wtf.Controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Client")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @RequestMapping("/")
    public ResponseEntity<List<Client>> getAll(){return ResponseEntity.ok(clientService.allClients());}
}
