package com.example.demo.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.library.service.LibraryService;
import com.example.demo.library.dto.BorrowRequest;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {
    private final LibraryService svc;

    public LibraryController(LibraryService svc) { this.svc = svc; }

    @GetMapping("/books")
    public List<?> allBooks() { return svc.getAllBooks(); }

    @GetMapping("/books/search")
    public List<?> search(@RequestParam String q) { return svc.searchBooks(q); }

    @GetMapping("/books/available")
    public List<?> available() { return svc.getAvailableBooks(); }

    @GetMapping("/books/borrowed")
    public List<?> borrowed() { return svc.getBorrowedBooks(); }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrow(@RequestBody BorrowRequest req) {
        try {
            svc.borrowBook(req.getMemberName(), req.getBookTitle());
            return ResponseEntity.ok("Transaction successful!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestBody BorrowRequest req) {
        try {
            svc.returnBook(req.getMemberName(), req.getBookTitle());
            return ResponseEntity.ok("Book returned successfully!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }

    @GetMapping("/transactions")
    public List<String> transactions() { return svc.getTransactionLogs(); }
}
