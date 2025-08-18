package com.example.book.repository;

import com.example.book.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("도서 등록 테스트")
    void testCreateBook() {
        // given
        Book newBook = new Book("스프링 부트 입문", "홍길동", "9788956746425", LocalDate.of(2025, 5, 7), 30000);

        // when
        Book savedBook = bookRepository.save(newBook);

        // then
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isGreaterThan(0);
        assertThat(savedBook.getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    @DisplayName("ISBN으로 도서 조회 테스트")
    void testFindByIsbn() {
        // given
        String isbn = "9788956746425";
        bookRepository.save(new Book("스프링 부트 입문", "홍길동", isbn, LocalDate.of(2025, 5, 7), 30000));

        // when
        Optional<Book> foundBook = bookRepository.findByIsbn(isbn);

        // then
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getIsbn()).isEqualTo(isbn);
    }

    @Test
    @DisplayName("저자명으로 도서 목록 조회 테스트")
    void testFindByAuthor() {
        // given
        String author = "박둘리";
        bookRepository.save(new Book("JPA 프로그래밍", author, "9788956746432", LocalDate.of(2025, 4, 30), 35000));
        bookRepository.save(new Book("자바의 정석", author, "9788956746449", LocalDate.of(2024, 1, 1), 32000));

        // when
        List<Book> books = bookRepository.findByAuthor(author);

        // then
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    @DisplayName("도서 정보 수정 테스트")
    void testUpdateBook() {
        // given
        Book savedBook = bookRepository.save(new Book("JPA 프로그래밍", "박둘리", "9788956746432", LocalDate.of(2025, 4, 30), 35000));
        String newTitle = "코어 자바";

        // when
        savedBook.setTitle(newTitle);
        Book updatedBook = bookRepository.save(savedBook);

        // then
        assertThat(updatedBook.getTitle()).isEqualTo(newTitle);
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void testDeleteBook() {
        // given
        Book savedBook = bookRepository.save(new Book("스프링 부트 입문", "홍길동", "9788956746425", LocalDate.of(2025, 5, 7), 30000));
        Long bookId = savedBook.getId();

        // when
        bookRepository.deleteById(bookId);

        // then
        Optional<Book> deletedBook = bookRepository.findById(bookId);
        assertThat(deletedBook).isNotPresent();
    }
}
