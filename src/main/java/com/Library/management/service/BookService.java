package com.Library.management.service;

import com.Library.management.dto.BookRequest;
import com.Library.management.entity.Book;
import com.Library.management.exception.ResourceNotFoundException;
import com.Library.management.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repo;

    public List<Book> listAll() { return repo.findAll(); }

    public Book getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    @Transactional
    public Book create(BookRequest req) {
        Book b = new Book();
        b.setTitle(req.getTitle());
        b.setAuthor(req.getAuthor());
        b.setIsbn(req.getIsbn());
        b.setPrice(req.getPrice());
        b.setStatus("AVAILABLE");
        return repo.save(b);
    }

    @Transactional
    public Book update(Long id, BookRequest req) {
        Book b = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        b.setTitle(req.getTitle());
        b.setAuthor(req.getAuthor());
        b.setIsbn(req.getIsbn());
        b.setPrice(req.getPrice());
        return repo.save(b);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Book", "id", id);
        repo.deleteById(id);
    }
}