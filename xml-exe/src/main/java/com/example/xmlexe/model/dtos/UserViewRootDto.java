package com.example.xmlexe.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserViewRootDto {

    @XmlElement(name = "user")
    private List<UserWithProductsDto> users;

    public UserViewRootDto() {
    }

    public List<UserWithProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithProductsDto> users) {
        this.users = users;
    }
}
