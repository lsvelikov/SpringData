package com.example.xmlexe.service;


import com.example.xmlexe.model.dtos.UserSeedDto;
import com.example.xmlexe.model.dtos.UserViewRootDto;
import com.example.xmlexe.model.dtos.UsersAndProductsRootDto;
import com.example.xmlexe.model.entity.User;

import java.util.List;

public interface UserService {

    long getUsersSize();

    void seedUsers(List<UserSeedDto> userSeedDtos);

    User getRandomUser();

    UserViewRootDto findAllUsersWithMoreThanOneSoldProducts();

    UsersAndProductsRootDto findAllUsersWithAtLeastOneSoldProduct();
}
