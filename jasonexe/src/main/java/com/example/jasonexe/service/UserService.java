package com.example.jasonexe.service;

import com.example.jasonexe.model.dtos.UserSoldDto;
import com.example.jasonexe.model.dtos.UsersAndProductsDto;
import com.example.jasonexe.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDto> findAllUsersWithMoreThanOneSoldProduct();

    UsersAndProductsDto findAllUsersWithAtLeastOneSoldProduct();
}

