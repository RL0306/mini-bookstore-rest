package com.example.bookstore.service.impl;

import com.example.bookstore.dto.BookCreateDTO;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.BookUpdateDTO;
import com.example.bookstore.entity.BookEntity;
import com.example.bookstore.exceptions.CustomException;
import com.example.bookstore.repository.BookstoreRepository;
import com.example.bookstore.service.BookstoreService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;


@SpringBootTest
class BookstoreServiceImpTest {

    @InjectMocks
    BookstoreServiceImp bookstoreService;

    BookEntity bookEntity;

    @Mock
    BookstoreRepository bookstoreRepository;


    @BeforeEach
    void init(){
        bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setName("test-name");
        bookEntity.setAuthor("test-author");
        bookEntity.setPages(300);
    }

    @DisplayName("Check book with valid id")
    @Test
    void getBookById() {
        Mockito.when(bookstoreRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        BookDTO expectedValue = new BookDTO("test-name", "test-author", 300);
        BookDTO actualResult = bookstoreService.getBookById(1L);

        Assertions.assertEquals(expectedValue.name(), actualResult.name());
        Assertions.assertEquals(expectedValue.author(), actualResult.author());
        Assertions.assertEquals(expectedValue.pages(), actualResult.pages());
    }

    @Test
    void getAllBooks() {
        List<BookEntity> bookEntities = List.of(
                new BookEntity(1L, "first-name", 1, "first-author"),
                new BookEntity(2L, "second-name", 2, "second-author"),
                new BookEntity(3L, "third-name", 3, "third-author")
        );

        Mockito.when(bookstoreRepository.findAll()).thenReturn(bookEntities);


    }

    @DisplayName("Check book is successfully created by verifying properties")
    @Test
    void createBook() {
        BookCreateDTO bookCreateDTO = new BookCreateDTO("test-name", "test-author", 300);
        BookDTO expectedValue = new BookDTO("test-name", "test-author", 300);
        BookDTO actualResult = bookstoreService.createBook(bookCreateDTO);

        Assertions.assertEquals(expectedValue.name(), actualResult.name());
        Assertions.assertEquals(expectedValue.author(), actualResult.author());
        Assertions.assertEquals(expectedValue.pages(), actualResult.pages());
    }

    @DisplayName("Check book is successfully deleted")
    @Test
    void deleteBook() {
        BookEntity bookEntity = new BookEntity(1L,"test-name", 300, "test-author");
        Mockito.when(bookstoreRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        /**
         * verify the delete method is called seeing as the service method is void
         */
        bookstoreService.deleteBook(1L);

        Mockito.verify(bookstoreRepository, times(1)).deleteById(1L);

    }

    @DisplayName("Check exception is thrown on delete when book does not exist")
    @Test
    void delete_book_that_does_not_exist(){
        Exception exception = Assertions.assertThrows(CustomException.class, () -> {
            bookstoreService.deleteBook(2L);
        });

        String expectedMessage = "Book with id: 2 cannot be found";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage,actualMessage);
    }

    @DisplayName("Check book is successfully updated")
    @Test
    void updateBook() {
        BookDTO expectedResult = new BookDTO("update-name", "update-author", 350);
        BookEntity bookEntity = new BookEntity(1L,"test-name", 300, "test-author");

        Mockito.when(bookstoreRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO("update-name", "update-author", 350);
        BookDTO actualResult = bookstoreService.updateBook(1L, bookUpdateDTO);


        Assertions.assertEquals(expectedResult.name(), actualResult.name());
        Assertions.assertEquals(expectedResult.author(), actualResult.author());
        Assertions.assertEquals(expectedResult.pages(), actualResult.pages());
    }
}