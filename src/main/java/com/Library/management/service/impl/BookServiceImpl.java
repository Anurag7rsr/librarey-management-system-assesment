package com.Library.management.service.impl;
import com.Library.management.dto.BookRequest;
import com.Library.management.entity.Book;
import com.Library.management.exception.ResourceNotFoundException;
import com.Library.management.repository.BookRepository;
import com.Library.management.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository repo;

    @Override
    public List<Book> listAll() {
        log.debug("Listing all books");
        return repo.findAll();
    }

    @Override
    public Book getById(Long id) {
        log.debug("Fetching book id={}", id);
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    @Override
    @Transactional
    public Book create(BookRequest req) {
        log.info("Creating book isbn={}", req.getIsbn());
        Book b = new Book();
        b.setTitle(req.getTitle());
        b.setAuthor(req.getAuthor());
        b.setIsbn(req.getIsbn());
        b.setPrice(req.getPrice());
        b.setStatus("AVAILABLE");
        Book saved = repo.save(b);
        log.debug("Book created id={}", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public Book update(Long id, BookRequest req) {
        log.info("Updating book id={}", id);
        Book existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

        existing.setTitle(req.getTitle());
        existing.setAuthor(req.getAuthor());
        existing.setIsbn(req.getIsbn());
        existing.setPrice(req.getPrice());

        Book saved = repo.save(existing);
        log.debug("Book updated id={}", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting book id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Attempt to delete non-existing book id={}", id);
            throw new ResourceNotFoundException("Book", "id", id);
        }
        repo.deleteById(id);
        log.debug("Book deleted id={}", id);
    }
}