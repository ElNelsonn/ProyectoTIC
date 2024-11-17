
package uy.edu.um.wtf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ClientRepository;
import uy.edu.um.wtf.repository.UserRepository;
import uy.edu.um.wtf.services.ClientService;


import jakarta.validation.Validator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    @Mock
    private ClientRepository clientRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Validator validator;
    @InjectMocks
    private ClientService clientService;

    @Test
    public void addClientTest() throws EntityAlreadyExistsException, InvalidDataException {
        Long id = 52329771L;
        LocalDate birthDay = LocalDate.of(2000, 1, 1);

        Client newClient = Client.builder()
                .identityCard(id)
                .name("Joaquin")
                .surname("Fiorina")
                .birthDate(birthDay)
                .email("joacofiorina@gmail.com")
                .password("encodedPassword")
                .build();

        when(userRepo.findUserByIdentityCard(id)).thenReturn(Optional.empty());
        when(userRepo.findUserByEmail("joacofiorina@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("1234567890")).thenReturn("encodedPassword");
        when(clientRepo.save(any(Client.class))).thenReturn(newClient);

        when(validator.validate(any(Client.class))).thenReturn(Collections.emptySet());

        Client savedClient = clientService.addClient(
                id,
                "Joaquin",
                "Fiorina",
                birthDay,
                null,
                null,
                "joacofiorina@gmail.com",
                "1234567890"
        );

        assertEquals("joacofiorina@gmail.com", savedClient.getEmail());
        assertEquals("encodedPassword", savedClient.getPassword());
        verify(clientRepo).save(any(Client.class));
        verify(userRepo).findUserByIdentityCard(id);
        verify(userRepo).findUserByEmail("joacofiorina@gmail.com");
        verify(passwordEncoder).encode("1234567890");
        verify(validator).validate(any(Client.class));

    }
}