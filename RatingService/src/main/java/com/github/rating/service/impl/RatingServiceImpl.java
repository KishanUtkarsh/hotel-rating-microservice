package com.github.rating.service.impl;

import com.github.rating.entities.Hotel;
import com.github.rating.entities.Rating;
import com.github.rating.repositories.RatingRepository;
import com.github.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Rating addRating(Rating rating) {
        String ratingId = UUID.randomUUID().toString();

        rating.setRatingId(ratingId);

        return repository.save(rating);
    }

    @Override
    public List<Rating> getAllRating() {
        List<Rating> ratings = repository.findAll();

        String uri = "http://HOTEL-SERVICE/hotels/";
        List<Rating> newRating = ratings.stream()
                .map(rating -> {
                    Hotel hotel = restTemplate.getForEntity(uri + rating.getHotelId(), Hotel.class).getBody();
                    rating.setHotel(hotel);
                    return rating;
                }).collect(Collectors.toList());

        return newRating;
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {

        List<Rating> ratings = repository.getRatingByUserId(userId);

        String uri = "http://HOTEL-SERVICE/hotels/";
        List<Rating> newRating = ratings.stream()
                .map(rating -> {
                    Hotel hotel = restTemplate.getForEntity(uri + rating.getHotelId(), Hotel.class).getBody();
                    rating.setHotel(hotel);
                    return rating;
                }).collect(Collectors.toList());

        return newRating;
    }

    @Override
    public String deleteRatingByUserId(String userId) {

        repository.deleteRatingByUserId(userId);

        return "All Rating with UserId "+userId+" is Deleted";
    }



    @Override
    public Rating updateRating(Rating rating) {
        return null;
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        return repository.getRatingByHotelId(hotelId);
    }
}
