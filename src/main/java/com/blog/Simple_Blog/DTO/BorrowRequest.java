package com.blog.Simple_Blog.DTO;

import java.time.LocalDate;

public class BorrowRequest {
    private Long bookId;
    private LocalDate returnDate;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
