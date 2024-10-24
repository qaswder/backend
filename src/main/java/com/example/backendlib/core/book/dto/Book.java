package com.example.backendlib.core.book.dto;

import com.example.backendlib.core.author.dto.Author;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lib_book")
public class Book {
    @Id
    @SequenceGenerator(name = "seq_gen_book", sequenceName = "seq_book", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_book")
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private GenreEnum genre;

    @Column(name = "publisher")
    private String publisher;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(GenreEnum genre) {
        this.genre = genre;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        authors.add(author);
        author.getBooks().add(this);
    }

    public void addAuthors(Collection<Author> authors){
        authors.forEach(this::addAuthor);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.getBooks().remove(this);
    }

    public void removeAuthors(Collection<Author> authors){
        authors.forEach(this::removeAuthor);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        return id != null && id.equals(((Book) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
