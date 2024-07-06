package com.rogue.tictactoewebsocket.controller;

import com.rogue.tictactoewebsocket.entities.GameHistory;
import com.rogue.tictactoewebsocket.entities.Rating;
import com.rogue.tictactoewebsocket.repositories.GameHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/game-history")
public class GameHistoryController {

    private final GameHistoryRepository gameHistoryRepository;

    @Autowired
    public GameHistoryController(GameHistoryRepository gameHistoryRepository) {
        this.gameHistoryRepository = gameHistoryRepository;
    }

    @GetMapping("/{playerId}")
    public List<GameHistory> getGameHistoryByPlayerId(@PathVariable String playerId) {
        List<GameHistory> player1History = gameHistoryRepository.findByPlayer1(playerId);
        List<GameHistory> player2History = gameHistoryRepository.findByPlayer2(playerId);

        List<GameHistory> mergedHistory = new ArrayList<>();
        mergedHistory.addAll(player1History);
        mergedHistory.addAll(player2History);

        return mergedHistory;
    }
    @PostMapping("/save")
    public GameHistory saveRating(@RequestBody GameHistory gameHistory) {
        return gameHistoryRepository.save(gameHistory);
    }
}
