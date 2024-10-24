package com.example.backendlib.core.book.converter;

import com.example.backendlib.core.book.dto.Book;
import com.example.backendlib.core.book.web.contract.BookView;
import com.example.backendlib.core.book.web.contract.BookViewWithAuthors;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {
    private final BookToBookView bookToBookView;
    private final BookToBookViewWithAuthors bookToBookViewWithAuthors;

    public BookConverter(BookToBookView bookToBookView,
                         BookToBookViewWithAuthors bookToBookViewWithAuthors) {
        this.bookToBookView = bookToBookView;
        this.bookToBookViewWithAuthors = bookToBookViewWithAuthors;
    }

    public BookView toView(@NonNull Book book){
        return bookToBookView.convert(book);
    }

    public BookViewWithAuthors toViewWithAuthors(@NonNull Book book){
        return bookToBookViewWithAuthors.convert(book);
    }
}
