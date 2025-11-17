package com.Library.management.service;

import com.Library.management.dto.BookRequest;
import com.Library.management.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> listAll();
    Book getById(Long id);
    Book create(BookRequest req);
    Book update(Long id, BookRequest req);
    void deleteById(Long id);
}