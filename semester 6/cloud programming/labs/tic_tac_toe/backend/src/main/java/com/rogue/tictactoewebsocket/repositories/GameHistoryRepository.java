package com.rogue.tictactoewebsocket.repositories;

import com.rogue.tictactoewebsocket.entities.GameHistory;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface GameHistoryRepository extends CrudRepository<GameHistory, Integer> {
    List<GameHistory> findByPlayer1(String player1);

    List<GameHistory> findByPlayer2(String player2);

}