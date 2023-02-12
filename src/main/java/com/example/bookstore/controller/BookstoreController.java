package com.example.bookstore.controller;

import com.example.bookstore.dto.BookApiWrapper;
import com.example.bookstore.dto.BookCreateDTO;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.BookUpdateDTO;
import com.example.bookstore.service.BookstoreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public class BookstoreController {
    private final BookstoreService bookstoreService;

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id){
        BookDTO bookById = bookstoreService.getBookById(id);
        return ResponseEntity.ok(bookById);
    }

    @GetMapping
    public ResponseEntity<BookApiWrapper> getAllBooks(){
        BookApiWrapper bookApiWrapper = bookstoreService.getAllBooks();
        return ResponseEntity.ok(bookApiWrapper);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookCreateDTO bookCreateDTO){
        BookDTO createdBook = bookstoreService.createBook(bookCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id){
        bookstoreService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id, @Valid @RequestBody BookUpdateDTO bookUpdateDTO){
        BookDTO bookDTO = bookstoreService.updateBook(id, bookUpdateDTO);
        return ResponseEntity.ok(bookDTO);
    }

}
