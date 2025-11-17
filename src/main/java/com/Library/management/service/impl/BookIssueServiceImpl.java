package com.Library.management.service.impl;

import com.Library.management.entity.Book;
import com.Library.management.entity.BookIssue;
import com.Library.management.entity.Member;
import com.Library.management.exception.ResourceNotFoundException;
import com.Library.management.repository.BookIssueRepository;
import com.Library.management.repository.BookRepository;
import com.Library.management.repository.MemberRepository;
import com.Library.management.service.BookIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookIssueServiceImpl implements BookIssueService {

    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final BookIssueRepository issueRepo;

    @Override
    @Transactional
    public BookIssue issueBook(Long bookId, Long memberId) {
        log.info("Issuing book id={} to member id={}", bookId, memberId);

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        if (!"AVAILABLE".equalsIgnoreCase(book.getStatus())) {
            log.warn("Book id={} not available, status={}", bookId, book.getStatus());
            throw new IllegalStateException("Book is not available for issue");
        }

        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", memberId));

        BookIssue issue = new BookIssue();
        issue.setBook(book);
        issue.setMember(member);
        issue.setIssueDate(LocalDate.now());
        issue.setReturnDate(null);

        // mark book as issued
        book.setStatus("ISSUED");
        bookRepo.save(book);

        BookIssue saved = issueRepo.save(issue);
        log.debug("BookIssue saved id={}", saved.getId());
        return saved;
    }
}