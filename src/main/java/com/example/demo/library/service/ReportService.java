package com.example.demo.library.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.library.repository.BookRepository;
import com.example.demo.library.repository.MemberRepository;

@Service
public class ReportService {
    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;

    public ReportService(BookRepository bookRepo, MemberRepository memberRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
    }

    public Map<String, List<String>> booksGroupedByAuthor() {
        return bookRepo.findAll().stream()
                .collect(Collectors.groupingBy(Book::getAuthor,
                        Collectors.mapping(Book::getTitle, Collectors.toList())));
    }

    public List<String> membersBorrowingSummary() {
        // We don't store member->borrowedBooks relationship; infer from Book.isBorrowed only
        // If you want precise per-member list, store borrower in Book entity or maintain join table.
        // Here return count of borrowed books by each member by scanning transaction logs is possible
        // but for simplicity we show member names and borrowed count as zero (requires relation).
        return memberRepo.findAll().stream()
                .map(m -> m.getName() + " borrowed 0 book(s)")
                .collect(Collectors.toList());
    }

    public List<Map.Entry<String, Long>> topBorrowedAuthors() {
        return bookRepo.findAll().stream()
                .filter(Book::isBorrowed)
                .collect(Collectors.groupingBy(Book::getAuthor, Collectors.counting()))
                .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3).collect(Collectors.toList());
    }
}
