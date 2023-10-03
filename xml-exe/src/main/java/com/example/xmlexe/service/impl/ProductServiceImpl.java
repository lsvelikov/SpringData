package com.example.xmlexe.service.impl;

import com.example.xmlexe.model.dtos.ProductSeedDto;
import com.example.xmlexe.model.dtos.ProductViewRootDto;
import com.example.xmlexe.model.dtos.ProductWithSellerDto;
import com.example.xmlexe.model.entity.Product;
import com.example.xmlexe.repository.ProductRepository;
import com.example.xmlexe.service.CategoryService;
import com.example.xmlexe.service.ProductService;
import com.example.xmlexe.service.UserService;
import com.example.xmlexe.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProducts(List<ProductSeedDto> products) {
        products
                .stream()
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setSeller(userService.getRandomUser());
                    if (product.getPrice().compareTo(BigDecimal.valueOf(600)) > 0) {
                        product.setBuyer(userService.getRandomUser());
                    }
                    product.setCategories(categoryService.getRandomCategory());
                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public ProductViewRootDto findAllProductsInRangeWithNoBuyer() {
        ProductViewRootDto productViewRootDto = new ProductViewRootDto();
        productViewRootDto
                .setProducts(productRepository
                        .findAllByPriceBetweenAndBuyerIsNullOrderByPrice
                                (BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L))
                        .stream()
                        .map(product -> {
                            ProductWithSellerDto productWithSellerDto =
                                    modelMapper.map(product, ProductWithSellerDto.class);
                            productWithSellerDto.setSeller((product.getSeller().getFirstName() == null
                            ? "" : product.getSeller().getFirstName())
                            +  " " + product.getSeller().getLastName());
                            return productWithSellerDto;
                        })
                        .collect(Collectors.toList()));

        return productViewRootDto;

    }

    @Override
    public long getProductsCount() {
        return productRepository.count();
    }
}
