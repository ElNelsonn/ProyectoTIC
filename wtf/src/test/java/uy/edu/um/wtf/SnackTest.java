package uy.edu.um.wtf;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.SnackRepository;
import uy.edu.um.wtf.services.SnackService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SnackTest {

    @Mock
    private SnackRepository snackRepo;

    @Mock
    private Validator validator;

    @InjectMocks
    private SnackService snackService;

    @Test
    public void addSnackTest() throws InvalidDataException, EntityAlreadyExistsException {
        Snack newSnack = Snack.builder()
                .name("Ositos gominola")
                .price(62L)
                .build();

        when(snackRepo.findSnackByName("Ositos gominola")).thenReturn(Optional.empty());
        when(snackRepo.save(any(Snack.class))).thenReturn(newSnack);

        Snack savedSnack = snackService.addSnack(
                "Ositos gominola",
                62L,
                null
        );
        assertEquals("Ositos gominola", savedSnack.getName());
        verify(snackRepo).save(ArgumentMatchers.any(Snack.class));
    }
}
