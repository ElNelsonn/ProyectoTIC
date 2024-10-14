package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Client")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/registro")
    public ResponseEntity<Client> clientRegistration() {




    }



















    

    @RequestMapping("/")
    public ResponseEntity<List<Client>> getAll(){return ResponseEntity.ok(clientService.allClients());}

    @RequestMapping("/{name}")
    public ResponseEntity<List<Client>> byName(@PathVariable("name") String name) {
        try {
            List<Client> found = clientService.byName(name);
            return ResponseEntity.ok(found);
        } catch (InvalidDataException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping("/{surname}")
    public ResponseEntity<List<Client>> bySurname(@PathVariable("surname") String surname){
        try {
            List<Client> found = clientService.bySurname(surname);
            return ResponseEntity.ok(found);
        } catch (InvalidDataException e) {
            return ResponseEntity.status(500).build();
        }
    }

}
