package com.example.xmlexe.service;

import com.example.xmlexe.model.dtos.ProductSeedDto;
import com.example.xmlexe.model.dtos.ProductViewRootDto;

import java.util.List;

public interface ProductService {
    long getProductsCount();

    void seedProducts(List<ProductSeedDto> products);

    ProductViewRootDto findAllProductsInRangeWithNoBuyer();
}
