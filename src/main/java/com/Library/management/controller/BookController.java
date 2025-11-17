package com.Library.management.controller;

import com.Library.management.dto.BookRequest;
import com.Library.management.dto.BookResponse;
import com.Library.management.dto.IssueResponse;
import com.Library.management.entity.Book;
import com.Library.management.entity.BookIssue;
import com.Library.management.service.BookIssueService;
import com.Library.management.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookIssueService issueService;

    @GetMapping("/books")
    public List<BookResponse> getAll() {
        return bookService.listAll().stream()
                .map(b -> new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPrice(), b.getStatus()))
                .collect(Collectors.toList());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
        Book b = bookService.getById(id);
        return ResponseEntity.ok(new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPrice(), b.getStatus()));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest req) {
        Book b = bookService.create(req);
        return ResponseEntity.ok(new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPrice(), b.getStatus()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @Valid @RequestBody BookRequest req) {
        Book b = bookService.update(id, req);
        return ResponseEntity.ok(new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPrice(), b.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/{id}/issue/{memberId}")
    public ResponseEntity<IssueResponse> issue(@PathVariable("id") Long bookId, @PathVariable Long memberId) {
        BookIssue issue = issueService.issueBook(bookId, memberId);
        IssueResponse resp = new IssueResponse(issue.getId(), issue.getBook().getId(), issue.getMember().getId(), issue.getIssueDate(), issue.getReturnDate());
        return ResponseEntity.ok(resp);
    }
}