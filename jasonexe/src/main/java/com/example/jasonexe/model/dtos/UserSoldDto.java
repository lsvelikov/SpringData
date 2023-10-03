package com.example.jasonexe.model.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.Set;

public class UserSoldDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Set<ProductWithBayerDto> soldProducts;

    public UserSoldDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ProductWithBayerDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<ProductWithBayerDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
