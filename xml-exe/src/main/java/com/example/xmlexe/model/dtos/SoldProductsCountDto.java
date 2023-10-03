package com.example.xmlexe.model.dtos;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsCountDto {
    @XmlAttribute
    private Integer count;

    @XmlElement(name = "product")
    private List<ProductNameAndPriceDto> soldProducts;

    public SoldProductsCountDto() {
    }

    public List<ProductNameAndPriceDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductNameAndPriceDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
