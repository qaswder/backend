package com.example.backendlib.core.book.handler;

import com.example.backendlib.core.author.AuthorService;
import com.example.backendlib.core.author.dto.Author;
import com.example.backendlib.core.book.BookService;
import com.example.backendlib.core.book.converter.BookConverter;
import com.example.backendlib.core.book.dto.Book;
import com.example.backendlib.core.book.dto.GenreEnum;
import com.example.backendlib.core.book.web.contract.*;
import com.example.backendlib.error.ConflictResourceException;
import com.example.backendlib.error.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookHandler {
    private final BookConverter bookConverter;
    private final BookService bookService;
    private final AuthorService authorService;

    public BookHandler(BookConverter bookConverter,
                       BookService bookService,
                       AuthorService authorService) {
        this.bookConverter = bookConverter;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public BookView handlerGetBookById(@NonNull Integer id) {
        return bookConverter.toView(
                bookService.getBookById(id)
                        .orElseThrow(() -> new NotFoundException("Книга с id=" + id + ", не найден"))
        );
    }

    public Page<BookView> handlerGetAllBooks(@NonNull Pageable pageable) {
        Page<Book> books = bookService.getAllBook(pageable);
        List<BookView> bookViewList = books.stream()
                .map(bookConverter::toView)
                .toList();
        return new PageImpl<>(bookViewList);
    }

    public Page<BookView> handlerGetBookByName(@NonNull String title,
                                               @NonNull Pageable pageable) {
        Page<Book> books = bookService.getBookByTitle(title, pageable);
        List<BookView> bookViewList = books.stream()
                .map(bookConverter::toView)
                .toList();
        return new PageImpl<>(bookViewList);
    }

    public BookView handlerCreateBook(@NonNull BookCreateReq req) {
        final Book book = new Book();
        book.setTitle(req.title());
        book.setGenre(GenreEnum.getGenreByCode(req.genre()));
        book.setPublisher(req.publisher());

        return bookConverter.toView(
                bookService.saveBook(book)
        );
    }

    public BookViewWithAuthors handlerAddAuthorsToBook(@NonNull Integer id, @NonNull BookCreateWithAuthorsReq req) {
        final Book book = bookService.getBookById(id)
                .orElseThrow(() -> new NotFoundException("Книга с id=" + id + ", не найден"));

        Set<Author> authors = req.authors().stream()
                .map(authorCreateReq -> {
                    Author author = new Author();
                    author.setSurname(authorCreateReq.surname());
                    author.setName(authorCreateReq.name());
                    author.setPatronymic(authorCreateReq.patronymic());
                    author.setCountry(authorCreateReq.country());
                    return author;
                }).collect(Collectors.toSet());

        try{
            if(book.getAuthors().isEmpty()){
                book.addAuthors(authorService.findOrCreateAuthors(authors));
            } else {
                throw new IllegalStateException("В книге уже записаны авторы");
            }
        } catch (IllegalStateException e){
            throw new ConflictResourceException(e.getMessage());
        }

        return bookConverter.toViewWithAuthors(
                bookService.saveBook(book)
        );
    }

    public BookView handlerUpdateBookById(@NonNull Integer id, @NonNull BookUpdateReq req) {
        final Book prototype = bookService.getReferenceOrNew(id);
        prototype.setTitle(req.title());
        prototype.setGenre(GenreEnum.valueOf(req.genre()));
        prototype.setPublisher(req.publisher());

        return bookConverter.toView(
                bookService.saveBook(prototype)
        );
    }

    public void handlerDeleteBookById(@NonNull Integer id) {
        bookService.deleteBookById(id);
    }
}
