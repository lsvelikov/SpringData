package com.example.xmlexe.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryViewRootDto {

    @XmlElement(name = "category")
    private List<CategoriesByProductCountDto> categories;

    public CategoryViewRootDto() {
    }

    public List<CategoriesByProductCountDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesByProductCountDto> categories) {
        this.categories = categories;
    }
}
