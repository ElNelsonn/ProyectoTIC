package uy.edu.um.wtf.converters;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.repository.ClientRepository;



@Component
public class ClientConverter implements Converter<String, Client> {

    @Autowired
    private ClientRepository clientRepo;

    public Client convert(String email) {

        return clientRepo.findClientByEmail(email).orElse(null);
    }







}

