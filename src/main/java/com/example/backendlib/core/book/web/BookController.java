package com.example.backendlib.core.book.web;

import com.example.backendlib.core.book.handler.BookHandler;
import com.example.backendlib.core.book.web.contract.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@Tag(name = "Книги")
public class BookController {
    private final BookHandler bookHandler;

    public BookController(BookHandler bookHandler) {
        this.bookHandler = bookHandler;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "Получение книги по id")
    public BookView getBookById(@PathVariable @NotNull Integer id) {
        return bookHandler.handlerGetBookById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Получение всех книг")
    public Page<BookView> getAllBooks(@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        return bookHandler.handlerGetAllBooks(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    @Operation(summary = "Получение книги по названию")
    public Page<BookView> getBookByTitle(@RequestParam @NotNull String title,
                                            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        return bookHandler.handlerGetBookByName(title, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    @Operation(summary = "Создание книги")
    public BookView createBook(@Valid @RequestBody @NotNull BookCreateReq req) {
        return bookHandler.handlerCreateBook(req);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    @Operation(summary = "Добавление авторов книги")
    public BookViewWithAuthors createBookWithAuthors(@PathVariable @NotNull Integer id,
                                                     @Valid @RequestBody @NotNull BookCreateWithAuthorsReq req) {
        return bookHandler.handlerAddAuthorsToBook(id, req);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Изменение книги")
    public BookView updateBookById(@PathVariable @NotNull Integer id,
                                       @Valid @RequestBody @NotNull BookUpdateReq req) {
        return bookHandler.handlerUpdateBookById(id, req);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление книги")
    public void deleteBookById(@PathVariable @NotNull Integer id) {
        bookHandler.handlerDeleteBookById(id);
    }
}
