package com.orakuma.servitus.servis.exceptions;

public class ServisNotFoundException extends RuntimeException {
    public ServisNotFoundException() {
    }

    public ServisNotFoundException(String message) {
        super(message);
    }
}
