package com.example.backendlib.core.author.web.contract;

import jakarta.validation.constraints.NotNull;

public record AuthorUpdateReq(
        @NotNull String surname,
        @NotNull String name,
        @NotNull String patronymic,
        @NotNull String country
) {
}
