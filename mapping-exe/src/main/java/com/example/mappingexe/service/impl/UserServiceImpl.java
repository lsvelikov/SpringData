package com.example.mappingexe.service.impl;

import com.example.mappingexe.model.dto.OwnedGamesDto;
import com.example.mappingexe.model.dto.UserLoginDto;
import com.example.mappingexe.model.dto.UserRegisterDto;
import com.example.mappingexe.model.entity.User;
import com.example.mappingexe.repository.UserRepository;
import com.example.mappingexe.service.UserService;
import com.example.mappingexe.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User userLoggedIn;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            System.out.println("Wrong confirmed password");
            return;
        }

        Set<ConstraintViolation<UserRegisterDto>> violations = validationUtil.getViolations(userRegisterDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }
        User user = modelMapper.map(userRegisterDto, User.class);
        userRepository.save(user);
    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {

        Set<ConstraintViolation<UserLoginDto>> violations = validationUtil.getViolations(userLoginDto);

        if (!violations.isEmpty()) {
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = userRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword())
                .orElse(null);

        if (user == null) {
            System.out.println("Incorrect username / password");
            return;
        }
        userLoggedIn = user;

    }

    @Override
    public void logout() {
        if (userLoggedIn == null) {
            System.out.println("Cannot logout. No user was logged in.");
        } else {
            userLoggedIn = null;
        }
    }

    @Override
    public List<OwnedGamesDto> printOwnedGames(User userLoggedIn) {
        return userRepository.findAllByUser(userLoggedIn.getId())
                .stream()
                .map(game -> modelMapper.map(game, OwnedGamesDto.class))
                .collect(Collectors.toList());
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }
}
