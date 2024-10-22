package com.example.backendlib.core.author.handler;

import com.example.backendlib.core.author.AuthorService;
import com.example.backendlib.core.author.converter.AuthorConverter;
import com.example.backendlib.core.author.dto.Author;
import com.example.backendlib.core.author.web.contract.AuthorCreateReq;
import com.example.backendlib.core.author.web.contract.AuthorView;
import com.example.backendlib.error.NotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AuthorHandler {

    private final AuthorConverter authorConverter;
    private final AuthorService authorService;

    public AuthorHandler(AuthorConverter authorConverter,
                         AuthorService authorService) {
        this.authorConverter = authorConverter;
        this.authorService = authorService;
    }

    public AuthorView handlerGetAuthorById(@NonNull Integer id){
        return authorConverter.toView(
                authorService.getAuthorById(id)
                        .orElseThrow(()->new NotFoundException("Автор с id="+ id +", не найден"))
        );
    }

    public AuthorView handlerCreateAuthor(@NonNull AuthorCreateReq req){
        final Author author = new Author();
        author.setSurname(req.surname());
        author.setName(req.name());
        author.setPatronymic(req.patronymic());
        author.setCountry(req.country());

        return authorConverter.toView(
                authorService.saveAuthor(author)
        );
    }
}
