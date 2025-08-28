package com.orakuma.servitus.contact.exceptions;

public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException() {}

    public ContactNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
