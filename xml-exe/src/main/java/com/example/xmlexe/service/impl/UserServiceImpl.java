package com.example.xmlexe.service.impl;

import com.example.xmlexe.model.dtos.*;
import com.example.xmlexe.model.entity.User;
import com.example.xmlexe.repository.UserRepository;
import com.example.xmlexe.service.UserService;
import com.example.xmlexe.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers(List<UserSeedDto> userSeedDtos) {
        userSeedDtos
                .stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public UserViewRootDto findAllUsersWithMoreThanOneSoldProducts() {
        UserViewRootDto userViewRootDto = new UserViewRootDto();

        userViewRootDto.setUsers(
                userRepository.findAllUsersWithMoreThanOneSoldProduct()
                        .stream()
                        .map(user -> modelMapper.map(user, UserWithProductsDto.class))
                        .collect(Collectors.toList()));

        return userViewRootDto;
    }

    @Override
    public UsersAndProductsRootDto findAllUsersWithAtLeastOneSoldProduct() {
        UsersAndProductsRootDto usersAndProductsRootDto = new UsersAndProductsRootDto();

        usersAndProductsRootDto.setUsers(
                userRepository.findUsersAndProducts()
                        .stream()
                        .map(user -> modelMapper.map(user, UsersWithAgeSoldProductsDto.class))
                        .collect(Collectors.toList())
        );
        return usersAndProductsRootDto;
    }

    @Override
    public long getUsersSize() {
        return userRepository.count();
    }
}
