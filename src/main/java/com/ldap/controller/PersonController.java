package com.ldap.controller;

import com.ldap.model.Person;
import com.ldap.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;
    private String mobile;

    @PostMapping
    public List<Person> savePerson(@RequestParam String name, @RequestParam String mobile) {
        personRepository.create(Person
                .builder()
                .name(name)
                .mobile(mobile)
                .build());

        return personRepository.findAll();
    }

    @PutMapping
    public List<Person> updateMobile(@RequestParam String uid, @RequestParam String mobile) {
        personRepository.updateMobileNumber(uid, mobile);
        return personRepository.findAll();
    }

    @GetMapping
    public List<Person> getAll() {
        return personRepository.findAll();
    }

}
