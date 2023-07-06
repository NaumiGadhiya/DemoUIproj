package com.thymeleaf.repository;

import org.springframework.data.repository.CrudRepository;

import com.thymeleaf.entities.EmpEntity;

public interface EmpRepository extends CrudRepository<EmpEntity, Integer>{

    EmpEntity findByEmail(String email);

}
