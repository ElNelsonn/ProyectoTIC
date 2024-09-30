package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Cinema;
import uy.edu.um.wtf.entities.Screen;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.ScreenRepository;

import java.util.LinkedList;
import java.util.List;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    public Screen addScreen(String name, Cinema cinema, Integer columns, Integer rows) throws InvalidDataException {
        if (name==null||cinema==null||columns==null||rows==null||columns<=0||rows<=0){
            throw new InvalidDataException("The data in the new screen is incorrect");
        }
        if (name.trim().equals("")){
            throw new InvalidDataException("The values in name must be completed");
        }
        List<Boolean> seats = new LinkedList<Boolean>();
        for (int i = 0;i<150; i++) {
            seats.add(false);
        }

        Screen screenAux = Screen.builder()
                .cinema(cinema)
                .columms(columns)
                .rows(rows)
                .seats(seats)
                .name(name)
                .build();
        return screenRepository.save(screenAux);
    }

    public List<Screen> allScreens(){return screenRepository.findAll();}

    public List<Screen> byName(String name) throws InvalidDataException {
        if(name == null){
            throw new InvalidDataException("Value of name is null");
        }
        List<Screen> result = screenRepository.findScreensByName(name);
        return result;
    }

    public List<Screen> byCinema(Cinema cinema) throws InvalidDataException {
        if(cinema == null){
            throw new InvalidDataException("Value of cinema is null");
        }
        List<Screen> result = screenRepository.findScreensByCinema(cinema);
        return result;
    }

}
