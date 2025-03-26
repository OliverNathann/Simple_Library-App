package com.blog.Simple_Blog.repository;


import com.blog.Simple_Blog.model.Loan;
import com.blog.Simple_Blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByUserAndReturnedFalse(User user);
    @Query("SELECT l FROM Loan l WHERE l.returnDate < :currentDate")
    List<Loan> findOverdueLoans(@Param("currentDate") LocalDate currentDate);

}
