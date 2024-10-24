package com.example.backendlib.core.author.web.contract;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Автор")
public record AuthorView(
        @Schema(description = "Идентификатор")
        Integer id,
        @Schema(description = "Фамилия")
        String surname,
        @Schema(description = "Имя")
        String name,
        @Schema(description = "Отчество")
        String patronymic,
        @Schema(description = "Страна")
        String country
) {
}
