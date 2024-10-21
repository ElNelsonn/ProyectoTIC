package uy.edu.um.wtf.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
public class ClientRestController {

    @Autowired
    private ClientService clientService;


    

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
