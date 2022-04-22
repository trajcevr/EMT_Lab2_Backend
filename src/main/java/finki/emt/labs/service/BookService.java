package finki.emt.labs.service;

import finki.emt.labs.model.Book;
import finki.emt.labs.model.dto.BookDto;
import finki.emt.labs.model.enumerations.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> findByName(String name);

    Optional<Book> edit(Long id, String name, Category category, Long author, Integer availableCopies);

    Optional<Book> edit(Long id, BookDto bookDto);

    void decreaseAvailableCopies(Long id);

    Optional<Book> save(String name, Category category, Long author, Integer availableCopies);

    Optional<Book> save(BookDto bookDto);

    void deleteById(Long id);
}
