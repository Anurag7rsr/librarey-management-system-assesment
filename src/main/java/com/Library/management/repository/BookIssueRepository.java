package com.Library.management.repository;
import com.Library.management.entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookIssueRepository extends JpaRepository<BookIssue,Long> {
    Optional<BookIssue> findByBookIdAndReturnDateIsNull(Long bookId);
}
