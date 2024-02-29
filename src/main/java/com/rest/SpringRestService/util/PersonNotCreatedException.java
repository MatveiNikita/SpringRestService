package com.rest.SpringRestService.util;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String message) {
        super(message);
    }
}
