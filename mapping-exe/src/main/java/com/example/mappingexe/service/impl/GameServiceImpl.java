package com.example.mappingexe.service.impl;

import com.example.mappingexe.model.dto.AllGamesDto;
import com.example.mappingexe.model.dto.DetailGameDto;
import com.example.mappingexe.model.dto.GameAddDto;
import com.example.mappingexe.model.entity.Game;
import com.example.mappingexe.repository.GameRepository;
import com.example.mappingexe.service.GameService;
import com.example.mappingexe.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {
        Set<ConstraintViolation<GameAddDto>> violations = validationUtil.getViolations(gameAddDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        Game game = modelMapper.map(gameAddDto, Game.class);
        gameRepository.save(game);

        System.out.println("Added game " + gameAddDto.getTitle());
    }

    @Override
    public void editGame(Long id, BigDecimal price, double size) {
        Game game = gameRepository.findById(id).orElse(null);

        if (game == null) {
            System.out.println("There is no game with this id.");
            return;
        }
        if (size < 0 || price.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Please enter positive size and price");
            return;
        }
        game.setPrice(price);
        game.setSize(size);
        gameRepository.save(game);
        System.out.println("Edited " + game.getTitle());
    }

    @Override
    public void deleteGame(Long id) {
        Game game = gameRepository.findById(id).orElse(null);

        if (game == null) {
            System.out.println("There is no game with such id");
            return;
        }
        gameRepository.delete(game);
    }

    @Override
    public List<AllGamesDto> allGames() {
        return gameRepository.findAll()
                .stream()
                .map(game -> modelMapper.map(game, AllGamesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DetailGameDto detailGame(String title) {
        Game game = gameRepository.findGameByTitle(title);
        DetailGameDto detailGameDto = null;
        if (game != null) {
            detailGameDto = modelMapper.map(game, DetailGameDto.class);
        }
        return detailGameDto;
    }
}
