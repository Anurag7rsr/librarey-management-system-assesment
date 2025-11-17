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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookIssueService issueService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<BookResponse>> getAll() {
        List<BookResponse> list = bookService.listAll().stream()
                .map(b -> new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPrice(), b.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
        Book b = bookService.getById(id);
        BookResponse resp = new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPrice(), b.getStatus());
        return ResponseEntity.ok(resp);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest req) {
        Book b = bookService.create(req);
        BookResponse resp = new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPrice(), b.getStatus());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(b.getId())
                .toUri();

        return ResponseEntity.created(location).body(resp);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @Valid @RequestBody BookRequest req) {
        Book b = bookService.update(id, req);
        BookResponse resp = new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPrice(), b.getStatus());
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/issue/{memberId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<IssueResponse> issue(@PathVariable("id") Long bookId, @PathVariable Long memberId) {
        BookIssue issue = issueService.issueBook(bookId, memberId);
        IssueResponse resp = new IssueResponse(issue.getId(), issue.getBook().getId(), issue.getMember().getId(), issue.getIssueDate(), issue.getReturnDate());
        return ResponseEntity.ok(resp);
    }
}
