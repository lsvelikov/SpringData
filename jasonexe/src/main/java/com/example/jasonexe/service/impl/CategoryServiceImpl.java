package com.example.jasonexe.service.impl;

import com.example.jasonexe.costant.GlobalConstant;
import com.example.jasonexe.model.dtos.CategoryProductSumDto;
import com.example.jasonexe.model.dtos.CategorySeedDto;
import com.example.jasonexe.model.entity.Category;
import com.example.jasonexe.repository.CategoryRepository;
import com.example.jasonexe.service.CategoryService;
import com.example.jasonexe.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.jasonexe.costant.GlobalConstant.RESOURCES_FILES_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final String CATEGORIES_FILE_NAME = "categories.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() == 0) {

            String fileContent = Files.readString(Path.of(RESOURCES_FILES_PATH + CATEGORIES_FILE_NAME));


            CategorySeedDto[] categorySeedDtos = gson.fromJson(fileContent, CategorySeedDto[].class);

            Arrays.stream(categorySeedDtos)
                    .filter(validationUtil::isValid)
                    .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                    .forEach(categoryRepository::save);
        }
    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> categories = new HashSet<>();
        int number = ThreadLocalRandom.current().nextInt(1,3);

        long totalCategories = categoryRepository.count();
        for (int i = 0; i < number; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, totalCategories + 1);
            Category category = categoryRepository.findById(randomId).orElse(null);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public List<CategoryProductSumDto> categoriesByProductCount() {
        return categoryRepository.findCategorySummary();

    }
}
