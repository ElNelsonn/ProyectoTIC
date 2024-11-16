package uy.edu.um.wtf.controllers.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.wtf.DTO.ScreenForController;
import uy.edu.um.wtf.converters.CinemaConverter;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.entities.Screen;
import uy.edu.um.wtf.exceptions.EntityAlreadyExistsException;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.CinemaRepository;
import uy.edu.um.wtf.services.ScreenService;
import org.springframework.validation.BindingResult;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/screen")
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @Autowired
    private CinemaConverter cinemaConverter;

    @Autowired
    private CinemaRepository cinemaRepo;




    @GetMapping("/create")
    public String getCreateScreen(Model model, RedirectAttributes redirectAttributes){

        List<String> allCinemasNames = allCinemasNames();

        if (allCinemasNames == null) {

            redirectAttributes.addFlashAttribute("message", "No hay cine creados.");
            return "redirect:/administrator/home";
        }

        model.addAttribute("allCinemasNames", allCinemasNames);
        return "screen-creation" ;
    }

    @PostMapping("/create")
    public String createScreen(@ModelAttribute @Valid ScreenForController screen, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        List<String> errorMessages = new ArrayList<>();

        System.out.println(screen.getCinemaName());

        if (result.hasErrors()) {

            for (FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }

            model.addAttribute("errorMessages", errorMessages);

            List<String> allCinemasNames = allCinemasNames();
            model.addAttribute("allCinemasNames", allCinemasNames);

            return "screen-creation";
        }

        try {

            System.out.println(screen.getCinemaName());

            Screen newScreen = screenService.addScreen(
                    screen.getName(),
                    screen.getCinemaName(),
                    screen.getColumns(),
                    screen.getRows()
            );

            redirectAttributes.addFlashAttribute("message", "Sala creada con exito.");
            return "redirect:/administrator/home";

        } catch (EntityAlreadyExistsException | InvalidDataException | EntityNotFoundException e) {

            errorMessages.add(e.getMessage());
            model.addAttribute("errorMessages", errorMessages);

            List<String> allCinemasNames = allCinemasNames();
            model.addAttribute("allCinemasNames", allCinemasNames);

            return "screen-creation";
        }
    }


    private List<String> allCinemasNames() {

        List<Cinema> allCinemas = cinemaRepo.findAll();

        if (allCinemas.isEmpty()) {
            return null;
        }

        List<String> allCinemasNames = new LinkedList<>();

        for (Cinema cinema : allCinemas) {
            allCinemasNames.add(cinema.getName());
        }

        return allCinemasNames;
    }


}
