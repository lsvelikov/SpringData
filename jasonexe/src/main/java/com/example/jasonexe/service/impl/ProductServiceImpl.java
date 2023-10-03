package com.example.jasonexe.service.impl;

import com.example.jasonexe.model.dtos.ProductNameAndPriceDto;
import com.example.jasonexe.model.dtos.ProductSeedDto;
import com.example.jasonexe.model.entity.Product;
import com.example.jasonexe.repository.ProductRepository;
import com.example.jasonexe.service.CategoryService;
import com.example.jasonexe.service.ProductService;
import com.example.jasonexe.service.UserService;
import com.example.jasonexe.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.jasonexe.costant.GlobalConstant.RESOURCES_FILES_PATH;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT_FILE_NAME = "products.json";

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, ProductRepository productRepository, UserService userService, CategoryService categoryService) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProducts() throws IOException {
        if (productRepository.count() == 0) {
            String fileContent = Files.readString(Path.of(RESOURCES_FILES_PATH + PRODUCT_FILE_NAME));

            ProductSeedDto[] productSeedDtos = gson.fromJson(fileContent, ProductSeedDto[].class);

            Arrays.stream(productSeedDtos)
                    .filter(validationUtil::isValid)
                    .map(productSeedDto -> {
                        Product product = modelMapper.map(productSeedDto, Product.class);
                        product.setSeller(userService.findRandomUser());
                        if (product.getPrice().compareTo(BigDecimal.valueOf(500L)) < 0) {
                            product.setBuyer(userService.findRandomUser());
                        }
                        product.setCategories(categoryService.findRandomCategories());
                        return product;
                    })
                    .forEach(productRepository::save);
        }
    }

    @Override
    public List<ProductNameAndPriceDto> findAllProductsInRange(BigDecimal lower, BigDecimal upper) {
        return productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(lower, upper)
                .stream()
                .map(product -> {
                    ProductNameAndPriceDto productNameAndPriceDto = modelMapper
                            .map(product, ProductNameAndPriceDto.class);
                    productNameAndPriceDto.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

                    return productNameAndPriceDto;
                })
                .collect(Collectors.toList());
    }
}
