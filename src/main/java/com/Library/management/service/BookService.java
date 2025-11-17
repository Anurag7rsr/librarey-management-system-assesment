package com.Library.management.service;

import com.Library.management.entity.Book;
import com.Library.management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    @Autowired
    private final BookRepository bookRepository;

    public List<Book> all() {
        return bookRepository.findAll();
    }
}
