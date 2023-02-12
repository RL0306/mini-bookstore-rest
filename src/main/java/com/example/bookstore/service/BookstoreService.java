package com.example.bookstore.service;

import com.example.bookstore.dto.BookApiWrapper;
import com.example.bookstore.dto.BookCreateDTO;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.BookUpdateDTO;
import com.example.bookstore.entity.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookstoreService {
    BookDTO getBookById(Long id);
    BookApiWrapper getAllBooks();
    BookDTO createBook(BookCreateDTO bookCreateDTO);
    void deleteBook(Long id);
    BookDTO updateBook(Long id, BookUpdateDTO bookUpdateDTO);
}
