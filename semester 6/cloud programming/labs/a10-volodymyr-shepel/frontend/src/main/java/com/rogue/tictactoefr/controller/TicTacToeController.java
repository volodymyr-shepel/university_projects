package com.rogue.tictactoefr.controller;

import com.rogue.tictactoefr.service.UserService;
import com.rogue.tictactoefr.util.Game;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class TicTacToeController {



    private final UserService userService;

    @Autowired
    public TicTacToeController(UserService userService) {
        this.userService = userService;
    }

    @Value("${backend.ip}")
    private String ipAddress;

    @Value("${aws.s3.bucket}")
    private String s3Bucket;


    @GetMapping
    public ModelAndView index() {
        return ticTacToe();
    }

    @RequestMapping("/index")
    public ModelAndView ticTacToe() {
        ModelAndView modelAndView = new ModelAndView("index");
        String[][] board = new String[3][3];
        Arrays.stream(board).forEach(row -> Arrays.fill(row, " "));
        modelAndView.addObject("board", board);
        modelAndView.addObject("ipAddress", ipAddress);
        modelAndView.addObject("s3Bucket", s3Bucket);
        // LEFT FOR TESTING TODO:// implement logic to fetch images
        modelAndView.addObject("player1Image","p1.jpg");
        modelAndView.addObject("player2Image","p2.jpg");
        return modelAndView;
    }

    @GetMapping("/game-history")
    public ModelAndView getGameHistory() {
        ModelAndView modelAndView = new ModelAndView("game-history");
        modelAndView.addObject("ipAddress", ipAddress);
        //List<Game> gameHistory = getGameHistoryList();
        //modelAndView.addObject("gameHistory", gameHistory);
        return modelAndView;
    }

    @GetMapping("/customize-profile")
    public ModelAndView customizeProfile() {
        ModelAndView modelAndView = new ModelAndView("customize-profile");
        String userId = userService.getCurrentUser();
        modelAndView.addObject("ipAddress", ipAddress);
        modelAndView.addObject("s3Bucket", s3Bucket);
        return modelAndView;
    }

    @GetMapping("/leaderboard")
    public ModelAndView leaderboard() {
        ModelAndView modelAndView = new ModelAndView("leaderboard");
        String userId = userService.getCurrentUser();
        modelAndView.addObject("ipAddress", ipAddress);
        return modelAndView;
    }



}
