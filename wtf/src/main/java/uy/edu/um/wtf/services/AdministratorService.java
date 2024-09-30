package uy.edu.um.wtf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Administrator;
import uy.edu.um.wtf.repository.AdministratorRepository;

import java.util.List;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository AdminRepo;

    public List<Administrator> allAdministrators(){return AdminRepo.findAll();}


}
