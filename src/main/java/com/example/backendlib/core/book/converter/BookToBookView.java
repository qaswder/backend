package com.example.backendlib.core.book.converter;

import com.example.backendlib.core.book.dto.Book;
import com.example.backendlib.core.book.web.contract.BookView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookToBookView implements Converter<Book, BookView> {
    @Override
    public BookView convert(Book source) {
        return new BookView(
                source.getId(),
                source.getTitle(),
                source.getGenre().getCode(),
                source.getPublisher()
        );
    }
}
