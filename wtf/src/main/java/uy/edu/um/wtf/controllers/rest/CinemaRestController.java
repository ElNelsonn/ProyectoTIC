package uy.edu.um.wtf.controllers.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.CinemaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cinema")
public class CinemaRestController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/")
    public ResponseEntity<List<Cinema>> getAll(){return ResponseEntity.ok(cinemaService.allCinemas());}

    @GetMapping("/{location}")
    public ResponseEntity<List<Cinema>> byLocation(@PathVariable("location") String location){
        try {
            List<Cinema> found = cinemaService.byLocation(location);
            return ResponseEntity.ok(found);
        } catch (InvalidDataException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<Cinema>> byName(@PathVariable("name") String name) {
        try {
            Optional<Cinema> found = cinemaService.byName(name);
            return ResponseEntity.ok(found);
        } catch (InvalidDataException e){
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{mail}")
    public ResponseEntity<Optional<Cinema>> byMail(@PathVariable("mail") String mail) {
        try {
            Optional<Cinema> found = cinemaService.byMail(mail);
            return ResponseEntity.ok(found);
        } catch (InvalidDataException e) {
            return ResponseEntity.status(500).build();
        }

    }

}
