package com.example.mappingexe.service;

import com.example.mappingexe.model.dto.OwnedGamesDto;
import com.example.mappingexe.model.dto.UserLoginDto;
import com.example.mappingexe.model.dto.UserRegisterDto;
import com.example.mappingexe.model.entity.User;

import java.util.List;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logout();

    List<OwnedGamesDto> printOwnedGames(User userLoggedIn);

}
