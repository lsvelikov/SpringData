package com.example.jasonexe;

import com.example.jasonexe.model.dtos.CategoryProductSumDto;
import com.example.jasonexe.model.dtos.ProductNameAndPriceDto;
import com.example.jasonexe.model.dtos.UserSoldDto;
import com.example.jasonexe.model.dtos.UsersAndProductsDto;
import com.example.jasonexe.service.CategoryService;
import com.example.jasonexe.service.ProductService;
import com.example.jasonexe.service.UserService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final String OUTPUT_PATH = "src/main/resources/files/output/";
    private static final String PRODUCTS_IN_RANGE_FILE_NAME = "products-in-range.json";
    private static final String USER_SOLD_PRODUCTS_FILE_NAME = "user-sold-products.json";
    private static final String CATEGORIES_BY_PRODUCTS_FILE_NAME = "categories-by-products.json";
    private static final String USERS_AND_PRODUCTS_FILE_NAME = "users-and-products.json";

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private final Gson gson;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        seedData();

        System.out.println("Please enter exercise:");
        int exeNumber = Integer.parseInt(bufferedReader.readLine());

        switch (exeNumber) {
            case 1 -> productsInRange();
            case 2 -> soldProducts();
            case 3 -> categoriesByProductsCount();
            case 4 -> usersAndProducts();
        }
    }

    private void usersAndProducts() throws IOException {
        UsersAndProductsDto allUsersWithAtLeastOneSoldProduct = userService.findAllUsersWithAtLeastOneSoldProduct();

        String content = gson.toJson(allUsersWithAtLeastOneSoldProduct);

        writeToFile(OUTPUT_PATH + USERS_AND_PRODUCTS_FILE_NAME, content);
    }

    private void categoriesByProductsCount() throws IOException {
        List<CategoryProductSumDto> categoryProductSumDtos = categoryService.categoriesByProductCount();

        String content = gson.toJson(categoryProductSumDtos);

        writeToFile(OUTPUT_PATH + CATEGORIES_BY_PRODUCTS_FILE_NAME, content);
    }

    private void soldProducts() throws IOException {
        List<UserSoldDto> userSoldDtos = userService
                .findAllUsersWithMoreThanOneSoldProduct();

        String content = gson.toJson(userSoldDtos);

        writeToFile(OUTPUT_PATH + USER_SOLD_PRODUCTS_FILE_NAME, content);
    }

    private void productsInRange() throws IOException {
        List<ProductNameAndPriceDto> productNameAndPriceDtos = productService.
                findAllProductsInRange(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L));
        String content = gson.toJson(productNameAndPriceDtos);

        writeToFile(OUTPUT_PATH + PRODUCTS_IN_RANGE_FILE_NAME, content);
    }

    private void writeToFile(String filePath, String content) throws IOException {
        Files.write(Path.of(filePath), Collections.singleton(content));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
