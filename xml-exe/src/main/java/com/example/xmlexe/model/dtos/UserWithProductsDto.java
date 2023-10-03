package com.example.xmlexe.model.dtos;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithProductsDto {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElement(name = "product")
    @XmlElementWrapper(name = "sold-products")
    private List<UserWithSoldProductsDto> products;

    public UserWithProductsDto() {
    }

    public UserWithProductsDto(String firstName, String lastName, List<UserWithSoldProductsDto> products) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.products = products;
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

    public List<UserWithSoldProductsDto> getProducts() {
        return products;
    }

    public void setProducts(List<UserWithSoldProductsDto> products) {
        this.products = products;
    }
}
