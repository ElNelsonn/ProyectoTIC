package uy.edu.um.wtf.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.MovieScreening;
import uy.edu.um.wtf.services.MovieScreeningService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/MovieScreening")
public class MovieScreeningRestController {

    @Autowired
    private MovieScreeningService movieScreeningService;

    @RequestMapping("/")
    public ResponseEntity<List<MovieScreening>> getAll(){return ResponseEntity.ok(movieScreeningService.allMovieScreenings());}
}
