package com.blog.Simple_Blog.controller;

import com.blog.Simple_Blog.model.Loan;
import com.blog.Simple_Blog.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/library")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam Long bookId, @RequestParam LocalDate returnDate, Authentication authentication) {
        System.out.println("Authentication user: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());
        try {
            String username = authentication.getName();
            Loan loan = libraryService.borrowBook(username, bookId, returnDate);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/return/{loanId}")
    public ResponseEntity<?> returnBook(@PathVariable Long loanId, @RequestBody Map<String, String> requestBody, Authentication authentication) {
        System.out.println("Authentication user: " + authentication.getName());
        try {
            String username = authentication.getName();
            LocalDate returnDate = LocalDate.parse(requestBody.get("returnDate"));
            Loan loan = libraryService.returnBook(username, loanId, returnDate);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Book returned successfully");
            response.put("loanId", loan.getId());
            response.put("bookTitle", loan.getBook() != null ? loan.getBook().getTitle() : "Unknown");
            response.put("returnedBy", username);
            response.put("returnDate", loan.getReturnDate());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/overdue")
    public List<Loan> getOverdueLoans() {
        return libraryService.getOverdueLoans();
    }
}
