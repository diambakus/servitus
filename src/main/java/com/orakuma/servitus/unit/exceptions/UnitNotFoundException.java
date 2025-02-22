package com.orakuma.servitus.unit.exceptions;

public class UnitNotFoundException extends RuntimeException {
    public UnitNotFoundException() {
    }

    public UnitNotFoundException(String message) {
        super(message);
    }
}
