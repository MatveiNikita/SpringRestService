package com.rest.SpringRestService.controllers;

import com.rest.SpringRestService.dto.PersonDTO;
import com.rest.SpringRestService.service.Service;
import com.rest.SpringRestService.model.Person;
import com.rest.SpringRestService.util.PersonErrorResponse;
import com.rest.SpringRestService.util.PersonNotCreatedException;
import com.rest.SpringRestService.util.PersonNotfoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class MyOwmController {

    private final Service service;
    private final ModelMapper modelMapper;
    @Autowired
    public MyOwmController(Service service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PersonDTO> getAll(){
        return service.getAll().stream().map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getOne(@PathVariable("id") int id){
        return convertToPersonDTO(service.getOne(id));
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> setPerson(@RequestBody @Valid PersonDTO personDTO,
                                                BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error:
                 errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }

        service.addNewPerson(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
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

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotfoundException e){
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e){
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
    private PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }
}
