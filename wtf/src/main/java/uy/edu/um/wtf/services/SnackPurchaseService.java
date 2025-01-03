package uy.edu.um.wtf.services;

import jakarta.validation.Validator;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.entities.SnackPurchase;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ClientRepository;
import uy.edu.um.wtf.repository.SnackPurchaseRepository;
import uy.edu.um.wtf.repository.SnackRepository;
import uy.edu.um.wtf.utils.ValidationUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class
SnackPurchaseService {

    @Autowired
    private SnackPurchaseRepository snackPurchaseRepo;

    @Autowired
    private SnackRepository snackRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private Validator validator;

    public SnackPurchase addSnackPurchase(List<Snack> snacks, Client client, LocalDateTime date) throws InvalidDataException, EntityNotFoundException {

        if (clientRepo.findClientByIdentityCard(client.getIdentityCard()).isEmpty()) {
            throw new EntityNotFoundException("Cliente no encontrado.");
        }

        if (!snacksExist(snacks)) {
            throw new EntityNotFoundException("Algún snack no es valido.");
        }

        Long price = 0L;
        for (Snack snack : snacks) {
            price += snack.getPrice();
        }

        SnackPurchase newSnackPurchase = SnackPurchase.builder().
                snackList(snacks).
                client(client).
                date(date).
                totalPrice(price).
                build();

        ValidationUtil.validate(newSnackPurchase, validator);

        return snackPurchaseRepo.save(newSnackPurchase);
    }

    public boolean snacksExist(List<Snack> snacks) {
        for (Snack snack : snacks) {
            if (snackRepo.findSnackByName(snack.getName()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public List<SnackPurchase> allPurchasesByUser(String mail){
        return snackPurchaseRepo.findAllByClient_Email(mail);
    }

}
