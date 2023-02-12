package com.example.bookstore.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException{
    private String message;
    private int errorCode;

    public CustomException(String message, int errorCode){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
