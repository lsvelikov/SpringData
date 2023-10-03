package com.example.springintro.repository;

import com.example.springintro.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAllByFirstNameEndingWith(String endFirstName);
    @Query("select a.firstName, a.lastName, sum (ab.copies) as copies from Author a join a.books ab " +
            "group by a.id " +
            "order by copies desc")
    List<String> findAllByBooksCopies();
}