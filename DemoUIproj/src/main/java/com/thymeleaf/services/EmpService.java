package com.thymeleaf.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thymeleaf.repository.EmpRepository;
import com.thymeleaf.entities.EmpEntity;

@Service
public class EmpService {

    @Autowired
    private EmpRepository empRepository;

    public void addEmp(EmpEntity emp) {
        this.empRepository.save(emp);
    }

    public List<EmpEntity> findAllEmp() {
        return (List<EmpEntity>) this.empRepository.findAll();
    }

    public EmpEntity findEmpById(int id) {
        return this.empRepository.findById(id).get();
    }

    public EmpEntity findEmpByEmail(String email) {
        return this.empRepository.findByEmail(email);
    }

    public void deleteEmp(int id) {
        this.empRepository.deleteById(id);
    }

    public boolean validName(String name){
        String pattern = "^[a-zA-Z\\s]+$";
        Pattern p = Pattern.compile(pattern);
        Matcher match = p.matcher(name);
        if (match.find()){
            return false;
        }
        return true;
    }
    public boolean validPass1(String password){
        String pattern = "^([^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*).{8,}$";
        Pattern p = Pattern.compile(pattern);
        Matcher match = p.matcher(password);
        if (match.find()){
            return false;
        }
        return true;
    }

    public boolean validPass2(String password){
        String pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern p = Pattern.compile(pattern);
        Matcher match = p.matcher(password);
        if (match.find()){
            return false;
        }
        return true;
    }
}
