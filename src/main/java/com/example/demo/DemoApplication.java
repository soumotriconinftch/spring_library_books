package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.library.entity.Book;
import com.example.demo.library.entity.Member;
import com.example.demo.library.repository.BookRepository;
import com.example.demo.library.repository.MemberRepository;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // seed initial data
    @Bean
    CommandLineRunner seed(BookRepository bookRepo, MemberRepository memberRepo) {
        return args -> {
            if (bookRepo.count() == 0) {
                bookRepo.save(new Book("1984", "George Orwell"));
                bookRepo.save(new Book("Animal Farm", "George Orwell"));
                bookRepo.save(new Book("The Hobbit", "J.R.R. Tolkien"));
                bookRepo.save(new Book("The Silmarillion", "J.R.R. Tolkien"));
                bookRepo.save(new Book("To Kill a Mockingbird", "Harper Lee"));
                bookRepo.save(new Book("Brave New World", "Aldous Huxley"));
            }
            if (memberRepo.count() == 0) {
                memberRepo.save(new Member("Alice"));
                memberRepo.save(new Member("Bob"));
                memberRepo.save(new Member("Charlie"));
            }
        };
    }
}
