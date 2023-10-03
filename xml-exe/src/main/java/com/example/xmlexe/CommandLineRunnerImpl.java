package com.example.xmlexe;

import com.example.xmlexe.model.dtos.*;
import com.example.xmlexe.service.CategoryService;
import com.example.xmlexe.service.ProductService;
import com.example.xmlexe.service.UserService;
import com.example.xmlexe.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final String FILES_PATH = "src/main/resources/files/";
    private static final String OUT_FILES_PATH = "out/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_PATH = "users.xml";
    private static final String PRODUCTS_FILE_PATH = "products.xml";
    private static final String PRODUCTS_IN_RANGE = "products-in-range.xml";
    private static final String USERS_SOLD_PRODUCTS = "users-sold-products.xml";
    private static final String CATEGORIES_BY_PRODUCTS = "categories-by-products.xml";
    private static final String USERS_AND_PRODUCTS = "users-and-products.xml";

    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final UserService userService;
    private final BufferedReader bufferedReader;

    private final ProductService productService;

    public CommandLineRunnerImpl(XmlParser xmlParser, CategoryService categoryService, UserService userService, ProductService productService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Please enter exercise number:");
        int exeNumber = Integer.parseInt(bufferedReader.readLine());

        switch (exeNumber) {
            case 1 -> productsInRange();
            case 2 -> usersSoldProducts();
            case 3 -> categoriesByProducts();
            case 4 -> usersAndProducts();
        }
    }

    private void usersAndProducts() throws JAXBException {
        UsersAndProductsRootDto usersAndProductsRootDto =
                userService.findAllUsersWithAtLeastOneSoldProduct();

        xmlParser.writeToFile(FILES_PATH + OUT_FILES_PATH + USERS_AND_PRODUCTS, usersAndProductsRootDto);
    }

    private void categoriesByProducts() throws JAXBException {
        CategoryViewRootDto categoryViewRootDto =
                categoryService.findAllCategoriesOrderByProductCount();

        xmlParser.writeToFile(FILES_PATH + OUT_FILES_PATH + CATEGORIES_BY_PRODUCTS, categoryViewRootDto);
    }

    private void usersSoldProducts() throws JAXBException {
        UserViewRootDto userViewRootDto = userService.findAllUsersWithMoreThanOneSoldProducts();

        xmlParser.writeToFile(FILES_PATH + OUT_FILES_PATH + USERS_SOLD_PRODUCTS, userViewRootDto);
    }

    private void productsInRange() throws JAXBException {
        ProductViewRootDto productViewRootDto = productService.findAllProductsInRangeWithNoBuyer();

        xmlParser.writeToFile(FILES_PATH + OUT_FILES_PATH + PRODUCTS_IN_RANGE, productViewRootDto);
    }

    private void seedData() throws JAXBException, FileNotFoundException {
        if (categoryService.getCategorySize() == 0) {
            CategorySeedRootDto categorySeedRootDto = xmlParser.fromFile(FILES_PATH + CATEGORIES_FILE_NAME, CategorySeedRootDto.class);

            categoryService.seedCategories(categorySeedRootDto.getCategories());
        }
        if (userService.getUsersSize() == 0) {
            UsersRootSeedDto usersRootSeedDto = xmlParser.fromFile(FILES_PATH + USERS_FILE_PATH, UsersRootSeedDto.class);
            userService.seedUsers(usersRootSeedDto.getUserSeedDtos());
        }
        if (productService.getProductsCount() == 0) {
            ProductRootSeedDto productRootSeedDto = xmlParser.fromFile(FILES_PATH + PRODUCTS_FILE_PATH,ProductRootSeedDto.class);

            productService.seedProducts(productRootSeedDto.getProducts());
        }
    }
}
