package com.example.backendlib.core.author;

import com.example.backendlib.core.author.dto.Author;
import com.example.backendlib.core.author.dto.AuthorRepo;
import com.example.backendlib.core.book.BookService;
import com.example.backendlib.core.book.dto.Book;
import com.example.backendlib.error.ConflictResourceException;
import com.example.backendlib.error.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepo authorRepo;
    private final BookService bookService;

    public AuthorService(AuthorRepo authorRepo,
                         BookService bookService) {
        this.authorRepo = authorRepo;
        this.bookService = bookService;
    }

    @Transactional(readOnly = true)
    public Page<Author> getAllAuthor(Pageable pageable) {
        return authorRepo.findAllAuthor(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Author> getAuthorById(@NonNull Integer id) {
        return authorRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<Author> getAuthorByName(@NonNull String name, Pageable pageable) {
        return authorRepo.findAuthorByName(name, pageable);
    }

    @Transactional
    public Author findOrCreateAuthor(@NonNull Author author) {
        return authorRepo
                .findByFullName(author.getSurname(), author.getName(), author.getPatronymic())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setSurname(author.getSurname());
                    newAuthor.setName(author.getName());
                    newAuthor.setPatronymic(author.getPatronymic());
                    newAuthor.setCountry(author.getCountry());
                    return saveAuthor(newAuthor);
                });
    }

    @Transactional
    public Set<Author> findOrCreateAuthors(Set<Author> authors) {
        return authors.stream()
                .map(this::findOrCreateAuthor)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Author saveAuthor(@NonNull Author author) {
        try {
            return authorRepo.save(author);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictResourceException(e.getMessage());
        }
    }

    @Transactional
    public Author getReferenceOrNew(@Nullable Integer id) {
        return id == null ? new Author() : authorRepo.getReferenceById(id);
    }

    @Transactional
    public void deleteAuthorById(@NonNull Integer id) {
        Author author = getAuthorById(id)
                .orElseThrow(() -> new NotFoundException("Автор с id=" + id + ", не найден"));

        Collection<Book> books = author.getBooks();

        for (Book book : books) {
            if (book.getAuthors().size() < 2 &&
                    book.getAuthors().stream().anyMatch(a -> a.getId().equals(id))) {
                bookService.deleteBookById(book.getId());
            }
        }

        author.removeBooks(author.getBooks());
        authorRepo.delete(author);
    }
}
