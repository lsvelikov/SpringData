package com.example.xmlexe.model.dtos;

import jakarta.validation.constraints.Size;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductWithSellerDto {


    @XmlAttribute(name = "seller")
    private String seller;
    @XmlAttribute(name = "price")
    private BigDecimal price;
    @XmlAttribute(name = "name")
    private String name;


    public ProductWithSellerDto() {
    }

    public ProductWithSellerDto(String name, BigDecimal price, String seller) {
        this.name = name;
        this.price = price;
        this.seller = seller;
    }

    @Size(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
