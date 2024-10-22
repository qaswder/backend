package com.example.backendlib.core.author.converter;

import com.example.backendlib.core.author.dto.Author;
import com.example.backendlib.core.author.web.contract.AuthorView;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {
    private final AuthorToAuthorView authorToAuthorView;
    private final AuthorToAuthorViewWithBooks authorToAuthorViewWithBooks;

    public AuthorConverter(AuthorToAuthorView authorToAuthorView,
                           AuthorToAuthorViewWithBooks authorToAuthorViewWithBooks) {
        this.authorToAuthorView = authorToAuthorView;
        this.authorToAuthorViewWithBooks = authorToAuthorViewWithBooks;
    }

    public AuthorView toView(@NonNull Author author){
        return authorToAuthorView.convert(author);
    }
}
