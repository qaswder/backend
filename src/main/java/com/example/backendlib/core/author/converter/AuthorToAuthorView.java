package com.example.backendlib.core.author.converter;

import com.example.backendlib.core.author.dto.Author;
import com.example.backendlib.core.author.web.contract.AuthorView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AuthorToAuthorView implements Converter<Author, AuthorView> {

    @Override
    public AuthorView convert(@NonNull Author source) {
        return new AuthorView(
                source.getId(),
                source.getSurname(),
                source.getName(),
                source.getPatronymic(),
                source.getCountry()
        );
    }
}
