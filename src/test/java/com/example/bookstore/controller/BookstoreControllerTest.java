package com.example.bookstore.controller;

import com.example.bookstore.dto.BookApiWrapper;
import com.example.bookstore.dto.BookCreateDTO;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.BookUpdateDTO;
import com.example.bookstore.entity.BookEntity;
import com.example.bookstore.repository.BookstoreRepository;
import com.example.bookstore.service.BookstoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookstoreController.class)
class BookstoreControllerTest {
    /**
     * fixes below error (not sure why occurs) https://stackoverflow.com/questions/73511395/intellij-could-not-autowire-no-beans-of-mockmvc-type-found-but-test-is-ok
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookstoreService bookstoreService;

    @DisplayName(value = "Check we get correct data when passing correct id")
    @Test
    void getBookById() throws Exception {
        BookDTO bookDTO = new BookDTO("book1", "author", 1);
        Mockito.when(bookstoreService.getBookById(1L)).thenReturn(bookDTO);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/books/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookDTO.name()))
                .andExpect(jsonPath("$.author").value(bookDTO.author()))
                .andExpect(jsonPath("$.pages").value(bookDTO.pages()));
    }


    @DisplayName(value = "Check we get the correct list size of 3 when returning all books")
    @Test
    void getAllBooks() throws Exception {
        List<BookDTO> allBooks = List.of(
                new BookDTO("first", "first", 1),
                new BookDTO("second", "second", 1),
                new BookDTO("third", "third", 1)
        );
        BookApiWrapper bookApiWrapper = new BookApiWrapper(allBooks);

        Mockito.when(bookstoreService.getAllBooks()).thenReturn(bookApiWrapper);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()").value(3));
    }

    @DisplayName(value = "Check we get the correct response back when creating book. Ensure response has same request book details")
    @Test
    void createBook() throws Exception {
        BookCreateDTO bookCreateDTO = new BookCreateDTO("test-book", "test-author", 10);
        BookDTO bookDTO = new BookDTO(bookCreateDTO.name(), bookCreateDTO.author(), bookCreateDTO.pages());
        Mockito.when(bookstoreService.createBook(bookCreateDTO)).thenReturn(bookDTO);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/books")
                .content(asJsonString(bookCreateDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(bookCreateDTO.name()))
                .andExpect(jsonPath("$.author").value(bookDTO.author()))
                .andExpect(jsonPath("$.pages").value(bookDTO.pages()));
    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/books/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateBook() throws Exception {
        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO("test-book", "test-author", 10);
        mvc.perform(MockMvcRequestBuilders
                .put("/api/v1/books/1")
                .content(asJsonString(bookUpdateDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}