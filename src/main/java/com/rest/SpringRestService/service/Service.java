package com.rest.SpringRestService.service;

import com.rest.SpringRestService.repository.PersonRepository;
import com.rest.SpringRestService.model.Person;
import com.rest.SpringRestService.util.PersonNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class Service {

    private final PersonRepository personRepository;
    @Autowired
    public Service(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAll(){
        return personRepository.findAll();
    }

    public Person getOne(int x){
        Optional<Person> foundedPerson = personRepository.findById(x);
        return foundedPerson.orElseThrow(PersonNotfoundException::new);
    }
    @Transactional
    public void addNewPerson(Person person){
        enrichPerson(person);
        personRepository.save(person);
    }

    @Transactional
    public void deletePerson(int id){
        personRepository.deleteById(id);
    }

    @Transactional
    public void updatePerson(int id, Person updatedPerson){
        Optional<Person> optionalPerson = personRepository.findById(id);

        if (optionalPerson.isPresent()){
            Person upPerson = optionalPerson.get();

            upPerson.setAge(updatedPerson.getAge());
            upPerson.setName(updatedPerson.getName());
            upPerson.setEmail(updatedPerson.getEmail());

            personRepository.save(upPerson);
        }
    }
     public void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN");
    }
}
