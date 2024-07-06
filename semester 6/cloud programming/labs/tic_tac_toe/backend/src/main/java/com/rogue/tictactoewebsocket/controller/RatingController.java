package com.rogue.tictactoewebsocket.controller;

import com.rogue.tictactoewebsocket.entities.Rating;
import com.rogue.tictactoewebsocket.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @GetMapping("/{playerId}")
    public Optional<Rating> getRatingByPlayerId(@PathVariable String playerId) {
        return ratingRepository.findById(playerId);
    }

    // !!! used for testing TODO:should be removed since saving will be done via lambda function
    @PostMapping("/save")
    public Rating saveRating(@RequestBody Rating rating) {
        return ratingRepository.save(rating);
    }

    @GetMapping("/ranking")
    public List<Rating> getAllRatings() {
        List<Rating> ratingsList = new ArrayList<>();
        ratingRepository.findAll().forEach(ratingsList::add);
        return ratingsList;
    }
}
