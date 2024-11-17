package uy.edu.um.wtf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.services.MovieService;


import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class movieTest {

    @Mock
    private MovieRepository movieRepo;

    @Mock
    private Validator validator;

    @InjectMocks
    private MovieService movieService;

    @Test
    void addMovie_test() throws InvalidDataException, EntityAlreadyExistsException {
        List<String> directores = new LinkedList<>();
        List<String> actores = new LinkedList<>();
        List<String> categorias = new LinkedList<>();
        categorias.add("suspenso");
        directores.add("David Fincher");
        actores.add("Morgan Freeman");
        actores.add("Brad Pitt");
        Long duracion = 121L;

        Movie newMovie = Movie.builder()
                .title("Seven")
                .releaseDate(LocalDate.now())
                .directors(directores)
                .synopsis("Morgan Freeman y Brad Pitt interpretan a dos detectives que investigan una serie de asesinatos basados en los siete pecados capitales.")
                .actors(actores)
                .duration(duracion)
                .classification("+19")
                .categories(categorias)
                .build();

        when(movieRepo.findMovieByTitle("Seven")).thenReturn(Optional.empty());     // veo que funcione bien lo agregado
        when(movieRepo.save(any(Movie.class))).thenReturn(newMovie);

        Movie savedMovie = movieService.addMovie(
                "Seven",
                LocalDate.now(),
                directores,
                "Morgan Freeman y Brad Pitt interpretan a dos detectives que investigan una serie de asesinatos basados en los siete pecados capitales.",
                categorias,
                actores,
                duracion,
                "+19",
                "posterURL",
                "imageURL"
        );

        assertEquals("Seven", savedMovie.getTitle());
        verify(movieRepo).save(any(Movie.class));


    }
}

