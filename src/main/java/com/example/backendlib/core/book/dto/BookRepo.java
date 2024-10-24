package com.example.backendlib.core.book.dto;

import com.example.backendlib.core.author.dto.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "authors")
    @Query("select b from Book b")
    Page<Book> findAllBook(Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "authors")
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findById(@Param("id") Integer id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "authors")
    @Query("select b from Book b where b.title like %:searchTerm%")
    Page<Book> findBookByTitle(@Param("searchTerm") String searchTerm, Pageable pageable);
}
