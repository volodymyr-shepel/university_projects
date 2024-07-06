package com.rogue.tictactoefr.util;

public record Game(
        Integer gameId,
        String player1,
        String player2,
        String winner) {
}
