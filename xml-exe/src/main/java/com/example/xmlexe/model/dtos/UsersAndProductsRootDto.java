package com.example.xmlexe.model.dtos;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersAndProductsRootDto {

    @XmlAttribute(name = "users-count")
    private Integer usersCount;
    @XmlElement(name = "user")
    private List<UsersWithAgeSoldProductsDto> users;

    public UsersAndProductsRootDto() {
    }

    public UsersAndProductsRootDto( List<UsersWithAgeSoldProductsDto> users) {
        this.users = users;
        this.usersCount = users.size();
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UsersWithAgeSoldProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersWithAgeSoldProductsDto> users) {
        this.users = users;
    }
}
