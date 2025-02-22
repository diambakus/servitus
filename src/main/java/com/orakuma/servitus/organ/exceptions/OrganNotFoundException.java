package com.orakuma.servitus.organ.exceptions;

public class OrganNotFoundException extends RuntimeException {
    public OrganNotFoundException() {
    }

    public OrganNotFoundException(String message) {
        super(message);
    }
}
