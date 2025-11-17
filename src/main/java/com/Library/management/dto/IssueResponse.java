package com.Library.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponse {
    private Long id;
    private Long bookId;
    private Long memberId;
    private LocalDate issueDate;
    private LocalDate returnDate;
}