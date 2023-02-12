package com.example.bookstore.dto;

/**
 * Using a DTO to return instead of Entity (best practice) as u want to hide hidden info even though we are not in this
 * case. (Hiding id, don't really need to show)
 */

public record BookDTO(String name, String author, int pages){

}