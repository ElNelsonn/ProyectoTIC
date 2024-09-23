package uy.edu.um.wtf.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.MovieRepository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(String Title, Date ReleaseDate, List<String> Directors, String Synopsis
            , List<String> Categories, List<String> Actors, Time Duration,String Clasification) throws InvalidDataException {
        if (Title==null||ReleaseDate==null||Directors==null||Synopsis==null||
        Categories==null||Actors==null||Duration==null||Clasification==null){
            throw new InvalidDataException("The data in the new movie is incorrect");
        }
        if (Title.trim().equals("")||Clasification.trim().equals("")||Synopsis.trim().equals("")){
            throw new InvalidDataException("The values in Title,Clasification and Synopsis must be completed");
        }
        if (Duration.getHours() <= 0&&Duration.getMinutes() <= 0&&Duration.getSeconds()<=0){
            throw new InvalidDataException("The values of Time must be over 0");
        }

        Movie movieAux = Movie.builder()
                .title(Title)
                .actors(Actors)
                .clasification(Clasification)
                .synopsis(Synopsis)
                .directors(Directors)
                .categories(Categories)
                .releaseDate(ReleaseDate)
                .duration(Duration)
                .build();
        return movieRepository.save(movieAux);
    }

    public List<Movie> allMovies(){return movieRepository.findAll();}

}
