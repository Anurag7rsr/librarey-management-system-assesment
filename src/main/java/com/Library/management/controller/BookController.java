package com.Library.management.controller;

import com.Library.management.entity.Book;
import com.Library.management.service.BookIssueService;
import com.Library.management.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private final BookIssueService bookIssueService;
    @Autowired
    private final BookService bookService;

    @GetMapping
    public List<Book> allBook(){
        return  bookService.all();
    }

    @GetMapping("/{id}")
    public Book  getBook(@PathVariable Long id){
        return bookService.get(id);
    }

    @PostMapping
    public  Book add(@RequestBody BookRequest req){
        return bookService.add(req);
    }

    @PutMapping("/{id}")
    public  Book update(@PathVariable Long id,@RequestBody BookRequest req){
        return  bookService.update(id,req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        bookService.deletebyId(id);
        return  ResponseEntity.ok("Book deleted");
    }

    @PostMapping("/{bookId}/issue/{memberId}")
    public boolean issueBook(@PathVariable Long bookId,@PathVariable Long memberId){
        return  issueService(bookId,memberId);
    }


}
