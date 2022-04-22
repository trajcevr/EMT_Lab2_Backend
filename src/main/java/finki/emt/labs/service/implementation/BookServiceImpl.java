package finki.emt.labs.service.implementation;

import finki.emt.labs.model.Author;
import finki.emt.labs.model.Book;
import finki.emt.labs.model.dto.BookDto;
import finki.emt.labs.model.enumerations.Category;
import finki.emt.labs.model.exceptions.AuthorWithIdNotFound;
import finki.emt.labs.model.exceptions.BookWithIdNotFound;
import finki.emt.labs.repository.AuthorRepository;
import finki.emt.labs.repository.BookRepository;
import finki.emt.labs.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByName(String name) {
        return this.bookRepository.findByName(name);
    }

    @Override
    public Optional<Book> save(String name, Category category, Long author, Integer availableCopies) {
        Author author1 = this.authorRepository.findById(author).orElseThrow(() -> new AuthorWithIdNotFound(author));
        Book book = new Book(name, category, author1, availableCopies);

        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public Optional<Book> save(BookDto bookDto) {
        Author author = this.authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new AuthorWithIdNotFound(bookDto.getAuthorId()));
        Book book = new Book(bookDto.getName(), bookDto.getCategory(), author, bookDto.getAvailableCopies());
        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public Optional<Book> edit(Long id, String name, Category category, Long author, Integer availableCopies) {
        Book book = this.findById(id).orElseThrow(() -> new BookWithIdNotFound(id));
        Author author1 = this.authorRepository.findById(author).orElseThrow(() -> new AuthorWithIdNotFound(author));
        book.setAuthor(author1);
        book.setCategory(category);
        book.setAvailableCopies(availableCopies);
        book.setName(name);
        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public Optional<Book> edit(Long id, BookDto bookDto) {
        Book book = this.findById(id).orElseThrow(() -> new BookWithIdNotFound(id));
        Author author1 = this.authorRepository.findById(bookDto.getAuthorId()).orElseThrow(() -> new AuthorWithIdNotFound(bookDto.getAuthorId()));
        book.setAuthor(author1);
        book.setCategory(bookDto.getCategory());
        book.setAvailableCopies(bookDto.getAvailableCopies());
        book.setName(bookDto.getName());
        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public void decreaseAvailableCopies(Long id) {
        Book book = this.findById(id).orElseThrow(() -> new BookWithIdNotFound(id));
        if (book.getAvailableCopies() > 0) {
            Integer copies = book.getAvailableCopies() - 1;
            book.setAvailableCopies(copies);
            this.bookRepository.save(book);
        }

    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }
}
