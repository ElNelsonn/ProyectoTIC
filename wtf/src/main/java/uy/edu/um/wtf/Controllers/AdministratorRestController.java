package uy.edu.um.wtf.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.Administrator;
import uy.edu.um.wtf.services.AdministratorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/administrator")
public class AdministratorRestController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/")
    public ResponseEntity<List<Administrator>> getAll(){return ResponseEntity.ok(administratorService.allAdministrators());}
}
