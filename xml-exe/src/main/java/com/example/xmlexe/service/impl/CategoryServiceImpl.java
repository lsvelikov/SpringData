package com.example.xmlexe.service.impl;

import com.example.xmlexe.model.dtos.CategoriesByProductCountDto;
import com.example.xmlexe.model.dtos.CategorySeedDto;
import com.example.xmlexe.model.dtos.CategoryViewRootDto;
import com.example.xmlexe.model.entity.Category;
import com.example.xmlexe.repository.CategoryRepository;
import com.example.xmlexe.service.CategoryService;
import com.example.xmlexe.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(List<CategorySeedDto> categories) {
        categories
                .stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public long getCategorySize() {
        return categoryRepository.count();
    }

    @Override
    public Set<Category> getRandomCategory() {
        Set<Category> categories = new HashSet<>();
        long categoriesCount = categoryRepository.count();
        for (int i = 0; i < 3; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, categoriesCount + 1);

            categories.add(categoryRepository.findById(randomId).orElse(null));
        }

        return categories;
    }

    @Override
    public CategoryViewRootDto findAllCategoriesOrderByProductCount() {
        CategoryViewRootDto categoryViewRootDto = new CategoryViewRootDto();

        categoryViewRootDto.setCategories(
                categoryRepository.findAllByProductCount()
                        .stream()
                        .map(categoriesByProductCountDto ->
                                modelMapper.map(categoriesByProductCountDto, CategoriesByProductCountDto.class))
                        .collect(Collectors.toList())
        );

        return categoryViewRootDto;

    }
}
