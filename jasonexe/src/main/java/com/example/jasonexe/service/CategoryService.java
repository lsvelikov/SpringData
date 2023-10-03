package com.example.jasonexe.service;

import com.example.jasonexe.model.dtos.CategoryProductSumDto;
import com.example.jasonexe.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seedCategories() throws IOException;

    Set<Category> findRandomCategories();

    List<CategoryProductSumDto> categoriesByProductCount();
}
