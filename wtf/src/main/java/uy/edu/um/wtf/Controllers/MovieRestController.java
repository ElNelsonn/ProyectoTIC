package uy.edu.um.wtf.Controllers;


import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.services.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Movie")
public class MovieRestController {

    @Autowired
    private MovieService movieService;

    @RequestMapping("/")
    public ResponseEntity<List<Movie>> getAll(){return ResponseEntity.ok(movieService.allMovies());}

    @RequestMapping("/{category}")
    public ResponseEntity<List<Movie>> getByCategory(@PathVariable("category") String category){
        try {
            List<Movie> found = movieService.byCategories(category);
            return ResponseEntity.ok(found);
        } catch (InvalidDataException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
