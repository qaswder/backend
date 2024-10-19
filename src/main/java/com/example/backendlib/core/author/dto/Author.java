package com.example.backendlib.core.author.dto;

import com.example.backendlib.core.book.dto.Book;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lib_author")
public class Author {
    @Id
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_author", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @Column(name = "id")
    private Integer id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "country")
    private String country;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "lib_author_book",
            joinColumns = {@JoinColumn(name = "id_author")},
            inverseJoinColumns = {@JoinColumn(name = "id_book")})
    private Set<Book> books = new HashSet<>();
}
