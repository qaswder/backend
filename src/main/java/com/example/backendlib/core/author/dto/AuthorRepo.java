package com.example.backendlib.core.author.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "books")
    @Query("select a from Author a")
    Page<Author> findAllAuthor(Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "books")
    @Query("select a from Author a where a.id = :id")
    Optional<Author> findById(@Param("id") Integer id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "books")
    @Query("select a from Author a where a.surname = :surname and a.name = :name and a.patronymic = :patronymic")
    Optional<Author> findByFullName(@Param("surname") String surname, @Param("name") String name, @Param("patronymic") String patronymic);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "books")
    @Query("select a from Author a where a.surname like %:searchTerm% or a.name like %:searchTerm% or a.patronymic like %:searchTerm%")
    Page<Author> findAuthorByName(@Param("searchTerm") String searchTerm, Pageable pageable);
}
