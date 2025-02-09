package com.appAziendaleMicroservizi.exceptions;

public class MyEntityNotFoundException extends RuntimeException {

    public MyEntityNotFoundException(String message) {
        super(message);
    }

}
