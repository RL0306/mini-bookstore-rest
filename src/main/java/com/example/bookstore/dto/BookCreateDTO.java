package com.example.bookstore.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

/**
 * Even though it's the same as BookDTO best to use different classes as different purposes and data might change
 */
public record BookCreateDTO(@NotEmpty(message = "Name is Mandatory") String name,
                            @NotEmpty(message = "Author is Mandatory") String author,
                            @Min(value = 1, message = "Must have at least 1 page") int pages) {
}
