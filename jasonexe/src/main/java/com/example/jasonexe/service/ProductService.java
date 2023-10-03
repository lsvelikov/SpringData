package com.example.jasonexe.service;

import com.example.jasonexe.model.dtos.ProductNameAndPriceDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts() throws IOException;

    List<ProductNameAndPriceDto> findAllProductsInRange(BigDecimal lower, BigDecimal upper);
}
