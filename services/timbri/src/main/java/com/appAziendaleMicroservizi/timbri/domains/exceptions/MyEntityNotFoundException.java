package com.appAziendaleMicroservizi.timbri.domains.exceptions;

public class MyEntityNotFoundException extends RuntimeException {

    public MyEntityNotFoundException(String message) {
        super(message);
    }

}
