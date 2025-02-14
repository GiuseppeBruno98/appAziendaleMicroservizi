package com.appAziendaleMicroservizi.curriculums.handler;


import com.appAziendaleMicroservizi.curriculums.domains.dto.responses.ErrorResponse;
import com.appAziendaleMicroservizi.curriculums.domains.exceptions.MyEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyEntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(MyEntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse
                        .builder()
                        .exception(MyEntityNotFoundException.class.getSimpleName())
                        .message(exception.getMessage())
                        .build());
    }


}
