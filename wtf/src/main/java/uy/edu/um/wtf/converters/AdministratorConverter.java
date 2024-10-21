package uy.edu.um.wtf.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import uy.edu.um.wtf.entities.Administrator;
import uy.edu.um.wtf.repository.AdministratorRepository;



@Component
public class AdministratorConverter implements Converter<String, Administrator> {

    @Autowired
    private AdministratorRepository administratorRepo;

    @Override
    public Administrator convert(String email) {

        return administratorRepo.findAdministratorByEmail(email).orElse(null);
    }



}
