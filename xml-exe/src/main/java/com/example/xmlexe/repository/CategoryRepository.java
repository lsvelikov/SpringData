package com.example.xmlexe.repository;

import com.example.xmlexe.model.dtos.CategoriesByProductCountDto;
import com.example.xmlexe.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT new com.example.xmlexe.model.dtos.CategoriesByProductCountDto " +
            "(c.name, COUNT (p.id), AVG (p.price), SUM (p.price)) " +
            "FROM Product p " +
            "JOIN p.categories c " +
            "GROUP BY c.id " +
            "ORDER BY COUNT (p.id) DESC ")
    List<CategoriesByProductCountDto> findAllByProductCount();
}

