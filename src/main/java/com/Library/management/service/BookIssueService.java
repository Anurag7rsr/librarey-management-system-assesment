package com.Library.management.service;

import com.Library.management.entity.Book;
import com.Library.management.entity.BookIssue;
import com.Library.management.entity.Member;
import com.Library.management.exception.ResourceNotFoundException;
import com.Library.management.repository.BookIssueRepository;
import com.Library.management.repository.BookRepository;
import com.Library.management.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookIssueService {
    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final BookIssueRepository issueRepo;

    @Transactional
    public BookIssue issueBook(Long bookId, Long memberId) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        if (!"AVAILABLE".equals(book.getStatus())) {
            throw new IllegalStateException("Book is not available for issue");
        }

        Member member = memberRepo.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Member", "id", memberId));

        // create issue
        BookIssue issue = new BookIssue();
        issue.setBook(book);
        issue.setMember(member);
        issue.setIssueDate(LocalDate.now());
        issue.setReturnDate(null);

        // set book status
        book.setStatus("ISSUED");
        bookRepo.save(book);

        return issueRepo.save(issue);
    }
}