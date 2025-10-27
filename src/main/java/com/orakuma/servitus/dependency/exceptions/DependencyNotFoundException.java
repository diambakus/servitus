package com.orakuma.servitus.dependency.exceptions;

public class DependencyNotFoundException extends RuntimeException {
    public DependencyNotFoundException() {}

    public DependencyNotFoundException(String message){
        super(message);
    }
}
