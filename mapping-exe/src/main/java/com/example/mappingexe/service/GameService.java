package com.example.mappingexe.service;

import com.example.mappingexe.model.dto.AllGamesDto;
import com.example.mappingexe.model.dto.DetailGameDto;
import com.example.mappingexe.model.dto.GameAddDto;
import com.example.mappingexe.model.entity.Game;

import java.math.BigDecimal;
import java.util.List;

public interface GameService {

    void addGame(GameAddDto gameAddDto);


    void editGame(Long id, BigDecimal price, double size);

    void deleteGame(Long id);

    List<AllGamesDto> allGames();

    DetailGameDto detailGame(String title);

}
