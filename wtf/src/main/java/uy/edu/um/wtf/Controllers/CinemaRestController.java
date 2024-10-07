package uy.edu.um.wtf.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.services.CinemaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cinema")
public class CinemaRestController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/")
    public ResponseEntity<List<Cinema>> getAll(){return ResponseEntity.ok(cinemaService.allCinemas());}
}
