package com.github.rating.repositories;

import com.github.rating.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating,String> {

    // Custom finder Methods

    List<Rating> getRatingByUserId(String userId);

    List<Rating> getRatingByHotelId(String hotelId);

    //Rating updateRating(Rating rating);

    void deleteRatingByUserId(String UserId);

}
