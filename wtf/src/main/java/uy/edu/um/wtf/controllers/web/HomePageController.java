package uy.edu.um.wtf.controllers.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uy.edu.um.wtf.entities.Movie;
import uy.edu.um.wtf.repository.MovieRepository;

import java.util.List;

@Controller("/")
public class HomePageController {

    @Autowired
    private MovieRepository movieRepo;

    @GetMapping("/home")
    public String getHomePage(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User usuario) {

        List<Movie> movies = movieRepo.findAll();

        model.addAttribute("movies", movies);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal().toString());

        //        if(isAuthenticated){
//            model.addAttribute("signed", isAuthenticated);
//            String rol = usuario.getAuthorities().iterator().next().getAuthority();
//            model.addAttribute("rol_user_actual", rol);
//        } else {
//            model.addAttribute("signed", !isAuthenticated);
//        }


        if (isAuthenticated) {
            model.addAttribute("signed", true);
            String rol = usuario.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("rol_user_actual", rol);
            System.out.println("Usuario autenticado con rol: " + rol);
        } else {
            model.addAttribute("signed", false);
            System.out.println("Usuario no autenticado.");
        }





//         if (usuario != null) {
//             model.addAttribute("signed", true);
//             String rol = usuario.getAuthorities().iterator().next().getAuthority();
//             model.addAttribute("rol_user_actual", rol);
//         } else
//             model.addAttribute("signed", false);

        return "home-page";
    }
}


