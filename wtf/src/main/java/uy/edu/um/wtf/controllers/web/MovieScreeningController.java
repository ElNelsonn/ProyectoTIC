package uy.edu.um.wtf.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.repository.ScreenRepository;
import uy.edu.um.wtf.services.MovieScreeningService;


@Controller
@RequestMapping("/moviescreening")
public class MovieScreeningController {

    @Autowired
    private MovieScreeningService movieScreeningService;


    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private ScreenRepository screenRepo;

    @GetMapping("/create")
    public String getMovieScreeningCreate() {


        return "moviescreening-creation";
    }


}

