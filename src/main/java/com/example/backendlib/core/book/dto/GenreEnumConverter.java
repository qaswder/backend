package com.example.backendlib.core.book.dto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class GenreEnumConverter implements AttributeConverter<GenreEnum, String> {
    @Override
    public String convertToDatabaseColumn(GenreEnum genreEnum) {
        if (genreEnum == null) {
            return null;
        }
        return genreEnum.getCode();
    }

    @Override
    public GenreEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(GenreEnum.values())
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
