package com.example.bookstore.repository;

import com.example.bookstore.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookstoreRepository extends JpaRepository<BookEntity, Long> {
}
