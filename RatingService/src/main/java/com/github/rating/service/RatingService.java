package com.github.rating.service;

import com.github.rating.entities.Rating;

import java.util.List;

public interface RatingService {

    Rating addRating(Rating rating);

    List<Rating> getAllRating();

    List<Rating> getRatingByUserId(String userId);

    String deleteRatingByUserId(String userId);

    Rating updateRating(Rating rating);

    List<Rating> getRatingByHotelId(String hotelId);
}
