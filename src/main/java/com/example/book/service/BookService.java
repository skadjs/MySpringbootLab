package com.example.book.service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;
import com.example.book.exception.BusinessException;
import com.example.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new BusinessException("이미 존재하는 ISBN입니다: " + request.getIsbn());
        }
        Book savedBook = bookRepository.save(request.toEntity());
        return BookDTO.BookResponse.from(savedBook);
    }

    public BookDTO.BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("ID에 해당하는 도서를 찾을 수 없습니다: " + id));
        return BookDTO.BookResponse.from(book);
    }

    public List<BookDTO.BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    public List<BookDTO.BookResponse> findBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("ID에 해당하는 도서를 찾을 수 없습니다: " + id));

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            existBook.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null && !request.getAuthor().isBlank()) {
            existBook.setAuthor(request.getAuthor());
        }
        if (request.getPrice() != null) {
            existBook.setPrice(request.getPrice());
        }
        if (request.getPublishDate() != null) {
            existBook.setPublishDate(request.getPublishDate());
        }
        return BookDTO.BookResponse.from(existBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException("ID에 해당하는 도서를 찾을 수 없습니다: " + id);
        }
        bookRepository.deleteById(id);
    }
}