package com.example.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record BookUpdateDTO(@NotEmpty(message = "Name is Mandatory") String name,
                            @NotEmpty(message = "Author is Mandatory") String author,
                            @Min(value = 1, message = "Must have at least 1 page") int pages) {
}
