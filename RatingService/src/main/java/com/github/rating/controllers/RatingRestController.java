package com.github.rating.controllers;

import com.github.rating.entities.Rating;
import com.github.rating.service.RatingService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ratings")
public class RatingRestController {

    @Autowired
    private RatingService service;

    Logger logger = LoggerFactory.getLogger(RatingRestController.class);

    @PostMapping
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating){

        Rating rating1 = service.addRating(rating);

        return ResponseEntity.status(HttpStatus.CREATED).body(rating1);
    }

    @GetMapping
    @RateLimiter(name = "HotelRateLimiter" , fallbackMethod = "rateLimiter")
    public ResponseEntity<List<Rating>> getAllRating(){

        return ResponseEntity.ok(service.getAllRating());
    }

    public ResponseEntity<List<Rating>> rateLimiter(Exception ex){
        logger.info("Rate limit exceeds : {}",ex.getMessage());
        return new ResponseEntity<>(new ArrayList<Rating>() , HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}")
    @Retry(name = "HotelCircuitBreaker",fallbackMethod = "hotelFault")
    public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable String userId){

        return ResponseEntity.ok(service.getRatingByUserId(userId));
    }

    int retry = 1;
    public ResponseEntity<List<Rating>> hotelFault(String userId,Exception ex){
        List<Rating> ratings = new ArrayList<>();
        logger.info("Hotel service is Slow Checking the connectivity... : {}" , retry);
        retry++;

        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingByHotelId(@PathVariable String hotelId){

        return ResponseEntity.ok(service.getRatingByHotelId(hotelId));
    }

    @PostMapping("/update")
    public ResponseEntity<Rating> updateRating(Rating rating){
        Rating updatedRating = service.updateRating(rating);
        return ResponseEntity.ok(updatedRating);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteRatingByUserId(@PathVariable String userId){
        String response = service.deleteRatingByUserId(userId);
        return ResponseEntity.ok(response);
    }

}
