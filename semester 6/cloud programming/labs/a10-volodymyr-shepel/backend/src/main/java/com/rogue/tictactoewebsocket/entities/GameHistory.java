package com.rogue.tictactoewebsocket.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "GameHistory")
public class GameHistory {

    private String gameId;
    private String player1;
    private String player2;
    private String result;

    @DynamoDBHashKey(attributeName = "gameId")
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "Player1Index", attributeName = "player1")
    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "Player2Index", attributeName = "player2")
    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "ResultIndex", attributeName = "result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
