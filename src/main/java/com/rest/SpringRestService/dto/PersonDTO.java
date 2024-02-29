package com.rest.SpringRestService.dto;

import com.rest.SpringRestService.model.Person;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PersonDTO {

    @Min(value = 12, message = "Age should be greater than 0")
    private int age;


    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 32, message = "Name should be between 2 and 30 characters")
    private String name;

    @Email
    @NotEmpty(message = "Email should not be empty ")
    private String email;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
