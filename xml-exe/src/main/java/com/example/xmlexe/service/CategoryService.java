package com.example.xmlexe.service;

import com.example.xmlexe.model.dtos.CategorySeedDto;
import com.example.xmlexe.model.dtos.CategoryViewRootDto;
import com.example.xmlexe.model.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(List<CategorySeedDto> categories);

    long getCategorySize();

    Set<Category> getRandomCategory();

    CategoryViewRootDto findAllCategoriesOrderByProductCount();

}
