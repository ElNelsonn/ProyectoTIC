package uy.edu.um.wtf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.services.SnackService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Snack")
public class SnackRestController {

    @Autowired
    private SnackService snackService;

    @RequestMapping("/")
    public ResponseEntity<List<Snack>> getAll(){return ResponseEntity.ok(snackService.allSnacks());}
}
