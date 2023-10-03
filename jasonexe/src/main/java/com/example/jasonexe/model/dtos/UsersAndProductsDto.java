package com.example.jasonexe.model.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UsersAndProductsDto {
    @Expose
    private Integer usersCount;
    @Expose
    private List<UsersSoldProductDto> users;

    public UsersAndProductsDto() {
    }

    public UsersAndProductsDto(List<UsersSoldProductDto> users) {
        this.users = users;
        this.usersCount = users.size();
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UsersSoldProductDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersSoldProductDto> users) {
        this.users = users;
    }
}
