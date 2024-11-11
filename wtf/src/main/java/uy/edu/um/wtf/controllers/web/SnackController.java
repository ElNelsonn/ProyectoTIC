package uy.edu.um.wtf.controllers.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.Snack;
import uy.edu.um.wtf.entities.SnackPurchase;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.SnackRepository;
import uy.edu.um.wtf.repository.UserRepository;
import uy.edu.um.wtf.services.SnackPurchaseService;
import uy.edu.um.wtf.services.SnackService;
import uy.edu.um.wtf.services.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/snack")
public class SnackController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private SnackService snackService;

    @Autowired
    private SnackRepository snackRepo;

    @Autowired
    private SnackPurchaseService snackPurchaseService;




    @GetMapping("/create")
    public String getCreateSnack() {

        return "snack-creation";
    }

    @PostMapping("/create")
    public String crateSnack(@ModelAttribute @Valid Snack snack, BindingResult result, Model model) {

        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }

            model.addAttribute("errorMessages", errorMessages);
            return "snack-creation";
        }

        try {

            Snack newSnack = snackService.addSnack(
                    snack.getName(),
                    snack.getPrice(),
                    snack.getImageURL()
            );

            return "client-signup-success";

        } catch (EntityAlreadyExistsException | InvalidDataException e) {

            errorMessages.add(e.getMessage());

            model.addAttribute("errorMessages", errorMessages);
            return "snack-creation";
        }
    }








    @GetMapping("/purchase")
    public String getSnacks(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario) {

        List<Snack> snacks = snackRepo.findAll();

        if (snacks.isEmpty()) {

            return "redirect:/home";
        }

        model.addAttribute("snacks", snacks);
        return "snack-purchase";
    }

    @PostMapping("/purchase")
    public String getSnacks(@RequestBody List<Snack> carrito, Model model) {

        if (carrito.isEmpty()) {

            return "redirect:/snack/purchase";
        }

        List<Snack> snacks = new LinkedList<>();

        for (Snack snack : carrito) {
            Optional<Snack> trueSnack = snackRepo.findSnackByName(snack.getName());

            if (trueSnack.isPresent()) {
                snacks.add(trueSnack.get());
            }

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        if (authentication != null && authentication.isAuthenticated()) {
            email = authentication.getName();

        } else {

            return "redirect:/snack/purchase";
        }

        Optional<User> clientOpt = userRepo.findUserByEmail(email);
        Client client = null;

        if (clientOpt.isPresent() && clientOpt.get() instanceof Client) {
            client = (Client) clientOpt.get();
        }


        try {

            SnackPurchase newSnackPurchase = snackPurchaseService.addSnackPurchase(
                    snacks,
                    client,
                    LocalDateTime.now()
            );

            System.out.println("Snacks comprados");

            return "redirect:/snack/purchase";

        } catch (EntityNotFoundException | InvalidDataException e) {

            List<String> errorMessages = new ArrayList<>();

            errorMessages.add(e.getMessage());

            model.addAttribute("errorMessages", errorMessages);
            model.addAttribute("snacks", snacks);
            System.out.println(errorMessages.get(0));
            return "snack-purchase";
        }

    }









    @GetMapping("/mypurchases")
    public String getPurchases(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario) {
        List<SnackPurchase> snacks = snackPurchaseService.allPurchasesByUser(usuario.getUsername());
        List<List<String>> bloqueTodaInfo = new LinkedList<>();

        if (snacks.isEmpty()) {
            model.addAttribute("errorMessage", "No hay compras realizadas ");
            return "redirect:/home";
        }

        for (SnackPurchase snackPurchase : snacks) {
            List<Snack> allSnacks = snackPurchase.getSnackList();
            List<String> infoSnack = new LinkedList<>(); // Crear una nueva lista para cada compra

            //cambio tipo de fecha apra caudno la muestre sea mas linda

            DateTimeFormatter fechaIni = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            DateTimeFormatter fechaSal = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            String nuevaFecha = LocalDateTime.parse(snackPurchase.getDate().toString(), fechaIni).format(fechaSal);

            infoSnack.add("Compra ID: " + snackPurchase.getId().toString() + ", " + "Fecha: " + nuevaFecha + ", " + "Snacks: ");

            for (Snack snack : allSnacks) {
                infoSnack.add(snack.getName());
            }

            bloqueTodaInfo.add(infoSnack);
        }

        model.addAttribute("bloqueTodaInfo", bloqueTodaInfo);
        return "compras";
    }


}
