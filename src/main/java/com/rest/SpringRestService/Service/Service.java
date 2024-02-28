package com.rest.SpringRestService.Service;

import com.rest.SpringRestService.Repository.PersonRepository;
import com.rest.SpringRestService.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class Service {

    private final PersonRepository personRepository;
    @Autowired
    public Service(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAll(){
        return personRepository.findAll();
    }

    public void addNewPerson(Person person){
        personRepository.save(person);
    }

    public void deletePerson(int id){
        personRepository.deleteById(id);
    }

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
}
