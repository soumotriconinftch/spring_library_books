package com.example.demo.library.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.library.entity.Book;
import com.example.demo.library.entity.Member;
import com.example.demo.library.entity.TransactionLog;
import com.example.demo.library.repository.BookRepository;
import com.example.demo.library.repository.MemberRepository;
import com.example.demo.library.repository.TransactionLogRepository;

@Service
public class LibraryService {
    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final TransactionLogRepository logRepo;

    public LibraryService(BookRepository bookRepo, MemberRepository memberRepo, TransactionLogRepository logRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
        this.logRepo = logRepo;
    }

    public List<Book> getAllBooks() { return bookRepo.findAll().stream().sorted((a,b)->a.getTitle().compareToIgnoreCase(b.getTitle())).collect(Collectors.toList()); }

    public List<Book> searchBooks(String keyword) {
        return bookRepo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword)
                .stream().sorted((a,b)->a.getAuthor().compareToIgnoreCase(b.getAuthor())).collect(Collectors.toList());
    }

    public List<Book> getAvailableBooks() { return bookRepo.findAllByIsBorrowedFalse(); }
    public List<Book> getBorrowedBooks() { return bookRepo.findAllByIsBorrowedTrue(); }

    @Transactional
    public void borrowBook(String memberName, String title) {
        Member member = memberRepo.findByNameIgnoreCase(memberName).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Book book = bookRepo.findByTitleIgnoreCase(title).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        if (book.isBorrowed()) throw new IllegalStateException("Book already borrowed");
        book.setBorrowed(true);
        bookRepo.save(book);
        String msg = String.format("%s borrowed '%s'", member.getName(), book.getTitle());
        logRepo.save(new TransactionLog(msg));
    }

    @Transactional
    public void returnBook(String memberName, String title) {
        Member member = memberRepo.findByNameIgnoreCase(memberName).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Book book = bookRepo.findByTitleIgnoreCase(title).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        if (!book.isBorrowed()) throw new IllegalStateException("Book is not borrowed");
        book.setBorrowed(false);
        bookRepo.save(book);
        String msg = String.format("%s returned '%s'", member.getName(), book.getTitle());
        logRepo.save(new TransactionLog(msg));
    }

    public List<String> getTransactionLogs() {
        return logRepo.findAll().stream()
                .map(l -> l.getOccurredAt() + ": " + l.getMessage())
                .collect(Collectors.toList());
    }
}
