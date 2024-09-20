package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.exceptions.InvalidDataException;
import uy.edu.um.wtf.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    public static UserRepository userRepository;

    public User addUser(String Name, String Surname, Long Id, Date Birth_date) throws InvalidDataException {
        if (Name == null || Surname == null || Birth_date == null)
        {
            throw new InvalidDataException("The data in the new User is incorrect");
        }
        if(Name.trim().equals("") || Surname.trim().equals(""))
        {
            throw new InvalidDataException("The data values in name and surname must the completed");
        }

        User userAux = User.builder()
                .Identity(Id)
                .Name(Name)
                .Surname(Surname)
                .Birth_date(Birth_date)
                .build();
        return userRepository.save(userAux);
    }

    public static List<User> allUsers(){return userRepository.findAll();}
}