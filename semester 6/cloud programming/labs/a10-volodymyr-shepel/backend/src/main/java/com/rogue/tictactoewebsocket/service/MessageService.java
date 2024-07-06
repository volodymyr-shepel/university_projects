package com.rogue.tictactoewebsocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rogue.tictactoewebsocket.model.TicTacToe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private final RestTemplate restTemplate;

    @Value("${aws.lambda.process-game-results-url}")
    private String processGameResultsUrl;

    public MessageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void invokeLambdaForResultsProcessing(TicTacToe game){
        logger.info("Invoking Lambda for results processing with game ID: {}", game.getGameId());

        // Prepare the request body
        String requestBody;
        try {
            requestBody = prepareRequestBody(game);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON for game ID: {}", game.getGameId(), e);
            return;
        }

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Send POST request
            ResponseEntity<String> response = restTemplate.exchange(processGameResultsUrl, HttpMethod.POST, entity, String.class);
            logger.info("Received response from Lambda for game ID: {}: {}", game.getGameId(), response.getStatusCode());
        } catch (Exception e) {
            logger.error("Error invoking Lambda for game ID: {}", game.getGameId(), e);
        }
    }

    private String prepareRequestBody(TicTacToe game) throws JsonProcessingException {
        logger.debug("Preparing request body for game ID: {}", game.getGameId());

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("gameId", game.getGameId());
        bodyMap.put("player1", game.getPlayer1());
        bodyMap.put("player2", game.getPlayer2());
        bodyMap.put("result", game.getWinner());

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bodyMap);
        logger.debug("Request body for game ID: {}: {}", game.getGameId(), requestBody);

        return requestBody;
    }
}
