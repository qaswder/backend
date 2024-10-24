package com.example.backendlib.core.book.web.contract;

import com.example.backendlib.core.author.web.contract.AuthorView;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Книга")
public record BookView(
        @Schema(description = "Идентификатор")
        Integer id,
        @Schema(description = "Название книги")
        String title,
        @Schema(description = "Жанр")
        String genre,
        @Schema(description = "Издатель")
        String publisher
) {
}
