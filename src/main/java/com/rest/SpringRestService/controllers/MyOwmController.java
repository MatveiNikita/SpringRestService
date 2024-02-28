package com.rest.SpringRestService.controllers;

import com.rest.SpringRestService.Service.Service;
import com.rest.SpringRestService.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class MyOwmController {

    private final Service service;

    @Autowired
    public MyOwmController(Service service) {
        this.service = service;
    }

    @GetMapping
    public List<Person> getAll(){
        return service.getAll();
    }

    @PostMapping("/create")
    public void setPerson(@RequestBody Person person){
        service.addNewPerson(person);
    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable("id") int id){
        service.deletePerson(id);
    }

    @PatchMapping("{id}")
    public void updatePerson(@PathVariable("id") int id,
                             @RequestBody Person person){
        service.updatePerson(id,person);
    }
}
