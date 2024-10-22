package com.example.backendlib.core.author.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "book")
    @Query("select a from Author a")
    Page<Author> findAllAuthor(Pageable pageable);
}
