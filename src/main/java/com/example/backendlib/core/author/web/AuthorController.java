package com.example.backendlib.core.author.web;

import com.example.backendlib.core.author.handler.AuthorHandler;
import com.example.backendlib.core.author.web.contract.AuthorCreateReq;
import com.example.backendlib.core.author.web.contract.AuthorUpdateReq;
import com.example.backendlib.core.author.web.contract.AuthorView;
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
@RequestMapping("/author")
@Tag(name = "Автор")
public class AuthorController {
    private final AuthorHandler authorHandler;

    public AuthorController(AuthorHandler authorHandler) {
        this.authorHandler = authorHandler;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "Получение автора по id")
    public AuthorView getAuthorById(@PathVariable @NotNull Integer id) {
        return authorHandler.handlerGetAuthorById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Получение всех авторов")
    public Page<AuthorView> getAllAuthors(@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        return authorHandler.handlerGetAllAuthors(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    @Operation(summary = "Получение автора по имени")
    public Page<AuthorView> getAuthorByName(@RequestParam @NotNull String name,
                                            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        return authorHandler.handlerGetAuthorByName(name, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    @Operation(summary = "Создание автора")
    public AuthorView createAuthor(@Valid @RequestBody @NotNull AuthorCreateReq req) {
        return authorHandler.handlerCreateAuthor(req);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Изменение автора")
    public AuthorView updateAuthorById(@PathVariable @NotNull Integer id,
                                       @Valid @RequestBody @NotNull AuthorUpdateReq req) {
        return authorHandler.handlerUpdateAuthorById(id, req);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление автора")
    public void deleteAuthorById(@PathVariable @NotNull Integer id) {
        authorHandler.handlerDeleteAuthorById(id);
    }
}
