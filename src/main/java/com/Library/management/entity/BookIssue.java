package com.Library.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "Books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    @ManyToOne @JoinColumn(name = "member_id")
    private Member member;


    private LocalDate issueDate;
    private LocalDate returnDate;
}
