package com.example.jasonexe.model.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SoldProductsCountDto {
    @Expose
    private Integer productCount;
    @Expose
    private List<SoldProductNameAndPriceDto> soldProducts;

    public SoldProductsCountDto() {
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public List<SoldProductNameAndPriceDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductNameAndPriceDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
