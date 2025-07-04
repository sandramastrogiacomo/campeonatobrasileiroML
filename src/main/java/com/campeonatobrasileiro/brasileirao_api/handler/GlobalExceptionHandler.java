package com.campeonatobrasileiro.brasileirao_api.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handlerValidationErrors (MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::messageFormat)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

        @ExceptionHandler (Exception.class)
    public ResponseEntity <Map<String, String>> handlerGenericException (Exception ex){
        Map<String,String> error = new HashMap<>();
        error.put("Erro interno inesperado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

        private String messageFormat(FieldError fieldError){
        return  fieldError.getField() + ": " + fieldError.getDefaultMessage();
        }

        @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlerEntityNotFound (EntityNotFoundException ex){
        Map<String,String> error = new HashMap<>();
        error.put("Erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

    }
