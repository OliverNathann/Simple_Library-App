package com.blog.Simple_Blog.service;

import com.blog.Simple_Blog.DTO.BorrowRequest;
import com.blog.Simple_Blog.model.Book;
import com.blog.Simple_Blog.model.Loan;
import com.blog.Simple_Blog.model.User;
import com.blog.Simple_Blog.repository.BookRepository;
import com.blog.Simple_Blog.repository.LoanRepository;
import com.blog.Simple_Blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    public Loan borrowBook(String username, Long bookId, LocalDate returnDate) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        LocalDate borrowDate = LocalDate.now();

        // Validasi: returnDate harus antara hari ini dan maksimal 30 hari dari sekarang
        if (returnDate.isBefore(borrowDate) || returnDate.isAfter(borrowDate.plusDays(30))) {
            throw new RuntimeException("Return date must be within 30 days from today.");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setBorrowDate(borrowDate);
        loan.setReturnDate(returnDate);
        loan.setReturned(false);

        return loanRepository.save(loan);
    }


    public Loan returnBook(String username, Long loanId, LocalDate returnDate) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getUser() == null || !loan.getUser().getUsername().equals(username)) {
            throw new RuntimeException("This loan does not belong to you");
        }

        loan.setReturned(true);
        loan.setReturnDate(returnDate);
        return loanRepository.save(loan);
    }

    public List<Loan> getOverdueLoans() {
        return loanRepository.findOverdueLoans(LocalDate.now());
    }

}

