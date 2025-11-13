package com.example.demo.library.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.library.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService svc;
    public ReportController(ReportService svc) { this.svc = svc; }

    @GetMapping("/authors")
    public Object authorReport() { return svc.booksGroupedByAuthor(); }

    @GetMapping("/top-authors")
    public Object topAuthors() { return svc.topBorrowedAuthors(); }
}
