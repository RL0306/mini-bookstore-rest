package com.example.bookstore.service.impl;

import com.example.bookstore.dto.BookApiWrapper;
import com.example.bookstore.dto.BookCreateDTO;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.BookUpdateDTO;
import com.example.bookstore.entity.BookEntity;
import com.example.bookstore.exceptions.CustomException;
import com.example.bookstore.repository.BookstoreRepository;
import com.example.bookstore.service.BookstoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookstoreServiceImp implements BookstoreService {

    private final BookstoreRepository bookstoreRepository;

    @Override
    public BookDTO getBookById(Long id) {
        BookEntity bookEntity =  bookstoreRepository.findById(id).orElseThrow(() -> new CustomException("Book with id: " + id +
                " cannot be found", 404));

        return mapEntityToDTO(bookEntity);
    }

    @Override
    public BookApiWrapper getAllBooks() {
        List<BookEntity> allBooks = bookstoreRepository.findAll();
        List<BookDTO> allBooksMappedToDTO = allBooks
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
        return new BookApiWrapper(allBooksMappedToDTO);
    }

    @Override
    public BookDTO createBook(BookCreateDTO bookCreateDTO) {
        BookEntity bookEntity = mapCreateDTOToEntity(bookCreateDTO);
        bookstoreRepository.save(bookEntity);
        return mapEntityToDTO(bookEntity);
    }

    @Override
    public void deleteBook(Long id) {
        //check the books exists before
        bookstoreRepository.findById(id).orElseThrow(() -> new CustomException("Book with id: " + id +
                " cannot be found", 404));
        bookstoreRepository.deleteById(id);
    }

    @Override
    public BookDTO updateBook(Long id, BookUpdateDTO bookUpdateDTO) {
        //check the books exists before
        BookEntity bookEntity =  bookstoreRepository.findById(id).orElseThrow(() -> new CustomException("Book with id: " + id +
                " cannot be found", 404));

        bookEntity.setAuthor(bookUpdateDTO.author());
        bookEntity.setName(bookUpdateDTO.name());
        bookEntity.setPages(bookUpdateDTO.pages());

        bookstoreRepository.save(bookEntity);

        return mapEntityToDTO(bookEntity);
    }

    /**
     * We need to convert the book entity to a dto object so that we can send this back in the response.
     * Created a method as use this multiple times
     * @param bookEntity
     * @return
     */
    public BookDTO mapEntityToDTO(BookEntity bookEntity){
        return new BookDTO(
                bookEntity.getName(),
                bookEntity.getAuthor(),
                bookEntity.getPages()
        );
    }

    /**
     * We need to convert the bookCreateDTO to an entity
     * Created a method as use this multiple times
     * @param bookCreateDTO
     * @return
     */
    public BookEntity mapCreateDTOToEntity(BookCreateDTO bookCreateDTO){
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName(bookCreateDTO.name());
        bookEntity.setAuthor(bookCreateDTO.author());
        bookEntity.setPages(bookCreateDTO.pages());
        return bookEntity;
    }
}
