package com.example.jasonexe.model.dtos;

import com.google.gson.annotations.Expose;

public class UsersSoldProductDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer age;
    @Expose
    private SoldProductsCountDto products;

    public UsersSoldProductDto() {
        this.products = new SoldProductsCountDto();
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SoldProductsCountDto getProducts() {
        return products;
    }

    public void setProducts(SoldProductsCountDto products) {
        this.products = products;
    }
}
