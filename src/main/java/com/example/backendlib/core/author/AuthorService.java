package com.example.backendlib.core.author;

import com.example.backendlib.core.author.dto.Author;
import com.example.backendlib.core.author.dto.AuthorRepo;
import com.example.backendlib.error.ConflictResourceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepo authorRepo;

    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Transactional(readOnly = true)
    public Page<Author> getAllAuthor(Pageable pageable){
        return authorRepo.findAllAuthor(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Author> getAuthorById(@NonNull Integer id){
        return authorRepo.findById(id);
    }

    @Transactional
    public Author saveAuthor(@NonNull Author author){
        try{
            return authorRepo.save(author);
        }catch (DataIntegrityViolationException e){
            throw new ConflictResourceException(e.getMessage());
        }
    }

    @Transactional
    public Author getReferenceOrNew(@Nullable Integer id){
        return id == null ? new Author() : authorRepo.getReferenceById(id);
    }

    @Transactional
    public void deleteAuthorById(@NonNull Integer id){
        authorRepo.deleteById(id);
    }
}
