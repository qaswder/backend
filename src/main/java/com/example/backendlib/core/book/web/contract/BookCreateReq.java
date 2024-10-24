package com.example.backendlib.core.book.web.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Создание книги")
public record BookCreateReq(
        @NotNull
        String title,
        @NotNull
        String genre,
        @NotNull
        String publisher
) {
}
