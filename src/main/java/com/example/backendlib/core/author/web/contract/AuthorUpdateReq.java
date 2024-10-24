package com.example.backendlib.core.author.web.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
@Schema(description = "Изменение автора")
public record AuthorUpdateReq(
        @NotBlank
        String surname,
        @NotBlank
        String name,
        @NotBlank
        String patronymic,
        @NotBlank
        String country
) {
}
