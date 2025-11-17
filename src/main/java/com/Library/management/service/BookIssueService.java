package com.Library.management.service;

import com.Library.management.entity.BookIssue;

public interface BookIssueService {
    BookIssue issueBook(Long bookId, Long memberId);
}
