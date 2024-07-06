package com.rogue.tictactoewebsocket.repositories;

import com.rogue.tictactoewebsocket.entities.Rating;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


@EnableScan
public interface RatingRepository extends CrudRepository<Rating, String> {
    List<Rating> findTop10ByOrderByScoreDesc();
}
