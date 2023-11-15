package com.korea.test.Book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;

  public List<Book> getBookList() {
    return bookRepository.findAll();
  }

  public Book getBookById(Long id) {
    Optional<Book> notebookOptional = bookRepository.findById(id);
    if(notebookOptional.isPresent()) {
      return notebookOptional.get();
    }

    throw new IllegalArgumentException("해당 노트북은 존재하지 않습니다.");
  }

  public Book writeBook() {
    Book book = new Book();
    book.setName("새노트");
    book.setCreateDate(LocalDateTime.now());

    return bookRepository.save(book);
  }
}
