package com.appAziendaleMicroservizi.curriculums.domains.exceptions;

public class MyEntityNotFoundException extends RuntimeException {

    public MyEntityNotFoundException(String message) {
        super(message);
    }

}
