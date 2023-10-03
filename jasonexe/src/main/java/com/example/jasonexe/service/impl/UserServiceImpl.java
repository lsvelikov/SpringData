package com.example.jasonexe.service.impl;

import com.example.jasonexe.model.dtos.*;
import com.example.jasonexe.model.entity.User;
import com.example.jasonexe.repository.UserRepository;
import com.example.jasonexe.service.UserService;
import com.example.jasonexe.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.jasonexe.costant.GlobalConstant.RESOURCES_FILES_PATH;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERS_FILE_NAME = "users.json";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() == 0) {
            Arrays.stream(gson.fromJson(
                            Files.readString(Path.of(RESOURCES_FILES_PATH + USERS_FILE_NAME)),
                            UserSeedDto[].class))
                    .filter(validationUtil::isValid)
                    .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                    .forEach(userRepository::save);

        }
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);

    }

    @Override
    public List<UserSoldDto> findAllUsersWithMoreThanOneSoldProduct() {
        return userRepository.findAllUsersWithMoreThanOneSoldProduct()
                .stream()
                .map(user -> modelMapper.map(user, UserSoldDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsersAndProductsDto findAllUsersWithAtLeastOneSoldProduct() {
        List<User> users = userRepository.findAllUsersWithAtLeastOneSoldProduct().orElse(null);

        List<UsersSoldProductDto> usersSoldProductDtoList = users
                .stream()
                .map(user -> {
                    UsersSoldProductDto usersSoldProductDto = modelMapper.map(user, UsersSoldProductDto.class);
                    usersSoldProductDto.getProducts().setProductCount(user.getSoldProducts().size());

                    return usersSoldProductDto;
                })
                .collect(Collectors.toList());

        UsersAndProductsDto usersAndProductsDto = new UsersAndProductsDto(usersSoldProductDtoList);
        return usersAndProductsDto;
    }
}
