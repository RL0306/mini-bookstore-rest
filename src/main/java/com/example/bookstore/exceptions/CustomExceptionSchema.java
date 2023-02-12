package com.example.bookstore.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomExceptionSchema {
    private String message;
    private int code;
    private LocalDateTime currentTime;
}
