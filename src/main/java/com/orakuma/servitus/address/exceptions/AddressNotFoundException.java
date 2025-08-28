package com.orakuma.servitus.address.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException() {
    }

    public AddressNotFoundException(String message) {
        super(message);
    }
}
