package com.rytech.bffagendadortarefas.infrastructure.exceptions;

public class IllegalArgumentsException extends RuntimeException {

    public IllegalArgumentsException(String message) {
        super(message);
    }

    public IllegalArgumentsException(String message, Throwable throwable){
        super(message, throwable);
    }
}
