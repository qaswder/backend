package com.example.backendlib.core.book.web.contract;

import com.example.backendlib.core.author.dto.Author;
import com.example.backendlib.core.author.web.contract.AuthorCreateReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Schema(description = "Добавление авторов в книгу")
public record BookCreateWithAuthorsReq(
        @NotNull
        @Size(min = 1)
        Set<AuthorCreateReq> authors
) {
}
