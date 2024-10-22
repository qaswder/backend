package com.example.backendlib.core.author.handler;

import com.example.backendlib.core.author.AuthorService;
import com.example.backendlib.core.author.converter.AuthorConverter;
import com.example.backendlib.core.author.dto.Author;
import com.example.backendlib.core.author.web.contract.AuthorCreateReq;
import com.example.backendlib.core.author.web.contract.AuthorUpdateReq;
import com.example.backendlib.core.author.web.contract.AuthorView;
import com.example.backendlib.error.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public Page<AuthorView> handlerGetAllAuthors(@NonNull Pageable pageable){
        Page<Author> authors = authorService.getAllAuthor(pageable);
        List<AuthorView> authorViewList = authors.stream()
                .map(authorConverter::toView)
                .toList();
        return new PageImpl<>(authorViewList);
    }

    public Page<AuthorView> handlerGetAuthorByName(@NonNull String name,
                                                   @NonNull Pageable pageable){
        Page<Author> authors = authorService.getAuthorByName(name, pageable);
        List<AuthorView> authorViewList = authors.stream()
                .map(authorConverter::toView)
                .toList();
        return new PageImpl<>(authorViewList);
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

    public AuthorView handlerUpdateAuthorById(@NonNull Integer id, @NonNull AuthorUpdateReq req){
        final Author prototype = authorService.getReferenceOrNew(id);
        prototype.setSurname(req.surname());
        prototype.setName(req.name());
        prototype.setPatronymic(req.patronymic());
        prototype.setCountry(req.country());

        return authorConverter.toView(
                authorService.saveAuthor(prototype)
        );
    }

    public void handlerDeleteAuthorById(@NonNull Integer id){
        authorService.deleteAuthorById(id);
    }
}
