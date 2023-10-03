package com.example.jasonexe.repository;

import com.example.jasonexe.model.dtos.CategoryProductSumDto;
import com.example.jasonexe.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT new com.example.jasonexe.model.dtos.CategoryProductSumDto" +
            "( c.name, count(p.id), avg(p.price), sum(p.price)) " +
            "FROM Product p " +
            "join p.categories c " +
            "group by c.id " +
            "order by count (p.id) DESC ")
    List<CategoryProductSumDto> findCategorySummary();
}
