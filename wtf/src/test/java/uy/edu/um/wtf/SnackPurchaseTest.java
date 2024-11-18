package uy.edu.um.wtf;

import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.entities.Snack;

import uy.edu.um.wtf.entities.SnackPurchase;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ClientRepository;
import uy.edu.um.wtf.repository.SnackPurchaseRepository;
import uy.edu.um.wtf.repository.SnackRepository;
import uy.edu.um.wtf.repository.UserRepository;
import uy.edu.um.wtf.services.ClientService;
import uy.edu.um.wtf.services.SnackPurchaseService;
import uy.edu.um.wtf.services.SnackService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SnackPurchaseTest {
    @Mock
    private SnackRepository snackRepo;
    @Mock
    private SnackPurchaseRepository snackPurchaseRepo;
    @Mock
    private ClientRepository clientRepo;
    @Mock
    private Validator validator;
    @Mock
    private SnackService snackService;
    @Mock
    private ClientService clientService;
    @InjectMocks
    private SnackPurchaseService snackPurchaseService;
    @Mock
    private UserRepository userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    private Client savedClient = new Client();
    private Snack savedSnack = new Snack();

    @BeforeEach
    public void setUp() throws InvalidDataException, EntityNotFoundException, EntityAlreadyExistsException {
        Long id = 52329771L;
        LocalDate birthDay = LocalDate.of(2000, 1, 1);
        savedClient = new Client();
        savedClient.setId(id);
        savedClient.setName("Joaquin");
        savedClient.setSurname("Fiorina");
        savedClient.setBirthDate(birthDay);
        savedClient.setEmail("joacofiorina@gmail.com");
        savedClient.setPassword("1234567890");

        when(clientRepo.findClientByIdentityCard(savedClient.getIdentityCard())).thenReturn(Optional.of(savedClient));

        savedSnack = new Snack();
        savedSnack.setName("Ositos gominola");
        savedSnack.setPrice(62L);
        savedSnack.setImageURL(null);

        when(snackRepo.findSnackByName(savedSnack.getName())).thenReturn(Optional.of(savedSnack));
    }

    @Test
    void addSnackPurchaseTest() throws InvalidDataException, EntityNotFoundException {
        System.out.println(savedClient.getId());
        System.out.println(clientRepo.findClientByIdentityCard(savedClient.getId()));
        System.out.println(snackRepo.findSnackByName(savedSnack.getName()));

        List<Snack> snackList = new LinkedList<>();
        snackList.add(savedSnack);
        LocalDateTime horarioHoy = LocalDateTime.now();
        SnackPurchase newSnackPurchase = SnackPurchase.builder()
                .snackList(snackList)
                .client(savedClient)
                .date(horarioHoy)
                .totalPrice(100L)
                .build();
        when(snackPurchaseRepo.save(any(SnackPurchase.class))).thenReturn(newSnackPurchase);
        System.out.println("casi creo el snack purchase");


        SnackPurchase savedSnackPurchase = snackPurchaseService.addSnackPurchase(
                snackList,
                savedClient,
                horarioHoy
        );

        verify(snackPurchaseRepo).save(any(SnackPurchase.class));
        assertEquals("joacofiorina@gmail.com", savedSnackPurchase.getClient().getEmail());

    }
}
