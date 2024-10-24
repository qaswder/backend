package com.example.backendlib.core.book.converter;

import com.example.backendlib.core.author.converter.AuthorConverter;
import com.example.backendlib.core.author.web.contract.AuthorView;
import com.example.backendlib.core.book.dto.Book;
import com.example.backendlib.core.book.web.contract.BookView;
import com.example.backendlib.core.book.web.contract.BookViewWithAuthors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookToBookViewWithAuthors implements Converter<Book, BookViewWithAuthors> {
    private final AuthorConverter authorConverter;

    public BookToBookViewWithAuthors(AuthorConverter authorConverter) {
        this.authorConverter = authorConverter;
    }

    @Override
    public BookViewWithAuthors convert(Book source) {
        Set<AuthorView> authorViews = source.getAuthors().stream()
                .map(authorConverter::toView)
                .collect(Collectors.toSet());
        return new BookViewWithAuthors(
                source.getId(),
                source.getTitle(),
                source.getGenre().getCode(),
                source.getPublisher(),
                authorViews
        );
    }
}
