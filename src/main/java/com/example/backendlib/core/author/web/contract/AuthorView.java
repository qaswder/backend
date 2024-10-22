package com.example.backendlib.core.author.web.contract;

public record AuthorView(
        Integer id,
        String surname,
        String name,
        String patronymic,
        String country
) {
}
