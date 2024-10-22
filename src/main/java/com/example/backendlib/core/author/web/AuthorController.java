package com.example.backendlib.core.author.web;

import com.example.backendlib.core.author.handler.AuthorHandler;
import com.example.backendlib.core.author.web.contract.AuthorCreateReq;
import com.example.backendlib.core.author.web.contract.AuthorView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    @GetMapping("/{id}")
    @Operation(summary = "Получение автора по id")
    public AuthorView getAuthorById(@PathVariable @NotNull Integer id){
        return authorHandler.handlerGetAuthorById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    @Operation(summary = "Создание автора")
    public AuthorView createAuthor(@Valid @RequestBody @NotNull AuthorCreateReq req){
        return authorHandler.handlerCreateAuthor(req);
    }
}
