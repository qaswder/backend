package com.example.backendlib.core.book;

import com.example.backendlib.core.book.dto.Book;
import com.example.backendlib.core.book.dto.BookRepo;
import com.example.backendlib.error.ConflictResourceException;
import com.example.backendlib.error.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Transactional(readOnly = true)
    public Page<Book> getAllBook(Pageable pageable){
        return bookRepo.findAllBook(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBookById(@NonNull Integer id){
        return bookRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<Book> getBookByTitle(@NonNull String title,Pageable pageable){
        return bookRepo.findBookByTitle(title, pageable);
    }

    @Transactional
    public Book saveBook(@NonNull Book book){
        try{
            return bookRepo.save(book);
        }catch (DataIntegrityViolationException e){
            throw new ConflictResourceException(e.getMessage());
        }
    }

    @Transactional
    public Book getReferenceOrNew(@Nullable Integer id){
        return id == null ? new Book() : bookRepo.getReferenceById(id);
    }

    @Transactional
    public void deleteBookById(@NonNull Integer id){
        Book book = getBookById(id)
                .orElseThrow(()->new NotFoundException("Книга с id="+ id +", не найдена"));

        book.removeAuthors(book.getAuthors());
        bookRepo.delete(book);
    }
}
