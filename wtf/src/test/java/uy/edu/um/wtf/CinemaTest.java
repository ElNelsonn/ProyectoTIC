package uy.edu.um.wtf;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uy.edu.um.wtf.entities.Cinema;

import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.CinemaRepository;
import uy.edu.um.wtf.services.CinemaService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CinemaTest {

    @Mock
    private CinemaRepository cinemaRepo;
    @Mock
    private Validator validator;
    @InjectMocks
    private CinemaService cinemaService;

    @Test
    public void addCinemaTest() throws InvalidDataException, EntityAlreadyExistsException {
        Cinema newCinema = Cinema.builder()
                .name("el mejor cine")
                .phoneNumber(Long.valueOf("095766156"))
                .location("rivera y comercio")
                .email("elMejor@gmail.com")
                .build();

        when(cinemaRepo.findCinemaByEmail("elMejor@gmail.com")).thenReturn(Optional.empty());
        when(cinemaRepo.save(any(Cinema.class))).thenReturn(newCinema);

        Cinema savedCinema = cinemaService.addCinema(
                "el mejor cine",
                (Long.valueOf("095766156")),
                "rivera y comercio",
                "elMejor@gmail.com"
        );

        assertEquals("elMejor@gmail.com", savedCinema.getEmail());
        verify(cinemaRepo).save(any(Cinema.class));

    }



}
