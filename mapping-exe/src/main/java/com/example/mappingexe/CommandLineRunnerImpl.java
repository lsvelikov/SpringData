package com.example.mappingexe;

import com.example.mappingexe.model.dto.*;
import com.example.mappingexe.model.entity.Game;
import com.example.mappingexe.service.GameService;
import com.example.mappingexe.service.UserService;
import com.example.mappingexe.service.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private UserServiceImpl userServiceImpl;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(UserService userService, GameService gameService, UserServiceImpl userServiceImpl) {
        this.userService = userService;
        this.gameService = gameService;
        this.userServiceImpl = userServiceImpl;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("Please enter your command:");
            String[] commands = bufferedReader.readLine().split("\\|");

            switch (commands[0]) {
                case "RegisterUser" -> userService.registerUser(new UserRegisterDto(commands[1], commands[2], commands[3], commands[4]));
                case "LoginUser" -> userService.loginUser(new UserLoginDto(commands[1], commands[2]));
                case "Logout" -> userService.logout();
                case "AddGame" -> {
                    if (!isLoggedUser()) {
                        return;
                    }
                    gameService
                            .addGame(new GameAddDto(commands[1], new BigDecimal(commands[2]),
                                    Double.parseDouble(commands[3]), commands[4], commands[5],
                                    commands[6], commands[7]));
                }
                case "EditGame" -> {
                    if (!isLoggedUser()) {
                        return;
                    }
                    gameService.editGame(Long.parseLong(commands[1]),
                            new BigDecimal(commands[2].split("=")[1]),
                            Double.parseDouble(commands[3].split("=")[1]));
                }
                case "DeleteGame" -> {
                    if (!isLoggedUser()) {
                        return;
                    }
                    gameService.deleteGame(Long.parseLong(commands[1]));
                }
                case "AllGames" -> printAllGames();
                case "DetailGame" -> printDetailGame(commands[1]);
                case "OwnedGames" -> printOwnedGames();
            }
        }
    }

    private void printOwnedGames() {
        if (!isLoggedUser()) {
            return;
        }
        List<OwnedGamesDto> ownedGames = userService.printOwnedGames(userServiceImpl.getUserLoggedIn());
        if (ownedGames.isEmpty()) {
            System.out.println("You don't own any games");
            return;
        }
        ownedGames.forEach(System.out::println);
    }

    private void printDetailGame(String title) {
        if (!isLoggedUser()) {
            return;
        }
        DetailGameDto detailGameDto = gameService.detailGame(title);
        if (detailGameDto == null) {
            System.out.println("There is no such a game");
            return;
        }
        System.out.println(detailGameDto);
    }

    private void printAllGames() {
        if (!isLoggedUser()) {
            return;
        }
        List<AllGamesDto> games = gameService.allGames();
        if (games.isEmpty()) {
            return;
        }
        games.forEach(System.out::println);
    }

    private boolean isLoggedUser() {
        if (userServiceImpl.getUserLoggedIn() == null) {
            System.out.println("Please login");
            return false;
        }
        return true;
    }
}
