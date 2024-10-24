package com.example.backendlib.core.book.dto;

import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

public enum GenreEnum {

    FANTASY("Фантастика"),
    SCIENCE_FICTION("Научная фантастика"),
    MYSTERY("Мистика"),
    ROMANCE("Романы"),
    COMEDY("Комедия"),
    THRILLER("Триллер"),
    HORROR("Ужасы"),
    DRAMA("Драма");

    GenreEnum(String code) {
        this.code = code;
    }

    private final String code;

    public String getCode() {
        return code;
    }

    public static GenreEnum getGenreByCode(@Nullable String code) {
        return Stream.of(GenreEnum.values())
                .filter(e -> Objects.equals(e.getCode(), code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
