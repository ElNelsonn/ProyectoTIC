package uy.edu.um.wtf.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.MovieRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(String title, LocalDate releaseDate, List<String> directors, String synopsis, List<String> categories, List<String> actors, Long duration, String clasification) throws InvalidDataException {
        if (title == null || releaseDate == null || directors == null||synopsis==null|| categories==null||actors==null||duration==null||clasification==null){
            throw new InvalidDataException("The data in the new movie is incorrect");
        }
        if (title.trim().equals("")||clasification.trim().equals("")||synopsis.trim().equals("")){
            throw new InvalidDataException("The values in Title,Clasification and Synopsis must be completed");
        }
        if (duration.intValue() <= 0){
            throw new InvalidDataException("The values of Time must be over 0");
        }

        Movie movieAux = Movie.builder()
                .title(title)
                .actors(actors)
                .classification(clasification)
                .synopsis(synopsis)
                .directors(directors)
                .categories(categories)
                .releaseDate(releaseDate)
                .duration(duration)
                .build();
        return movieRepository.save(movieAux);
    }

    public List<Movie> allMovies(){return movieRepository.findAll();}

}
