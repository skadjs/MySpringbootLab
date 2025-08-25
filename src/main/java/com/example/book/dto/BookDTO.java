package com.example.book.dto;

import com.example.book.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

public class BookDTO {

    @Getter
    @Setter
    public static class BookCreateRequest {

        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        @Size(max = 100)
        private String title;

        @NotBlank(message = "저자는 필수 입력 항목입니다.")
        @Size(max = 50)
        private String author;

        @NotBlank(message = "ISBN은 필수 입력 항목입니다.")
        @Size(min = 10, max = 13, message = "ISBN은 10자리 또는 13자리여야 합니다.")
        private String isbn;

        @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
        private double price;

        @PastOrPresent(message = "출판일은 현재 또는 과거 날짜여야 합니다.")
        private LocalDate publishDate;

        public Book toEntity() {
            return Book.builder()
                    .title(title).author(author).isbn(isbn)
                    .price(price).publishDate(publishDate)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class BookUpdateRequest {
        @Size(max = 100)
        private String title;
        @Size(max = 50)
        private String author;
        @PositiveOrZero
        private Double price;
        @PastOrPresent
        private LocalDate publishDate;
    }

    @Getter
    public static class BookResponse {
        private final Long id;
        private final String title;
        private final String author;
        private final String isbn;
        private final double price;
        private final LocalDate publishDate;

        public static BookResponse from(Book book) {
            return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(),
                    book.getIsbn(), book.getPrice(), book.getPublishDate());
        }

        private BookResponse(Long id, String title, String author, String isbn, double price, LocalDate publishDate) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.price = price;
            this.publishDate = publishDate;
        }
    }
}
