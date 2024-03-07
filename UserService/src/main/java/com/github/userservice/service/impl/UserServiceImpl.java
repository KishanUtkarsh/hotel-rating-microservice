package com.github.userservice.service.impl;

import com.github.userservice.entities.Rating;
import com.github.userservice.entities.User;
import com.github.userservice.exceptions.ResourceNotFoundException;
import com.github.userservice.external.service.RatingService;
import com.github.userservice.repositories.UserRepository;
import com.github.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingService ratingService;

    @Override
    public User saveUser(User user) {

        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> users =  userRepository.findAll();
        List<User> updatedUsers = users.stream().map(user -> {
            List<Rating> ratings = ratingService.getRatingByUserId(user.getUserId());
            user.setRatings(ratings);
            return user;
        }).collect(Collectors.toList());

        return updatedUsers;
    }

    @Override
    public User getUser(String userId) {
        User user =  userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with Given Id is not found on server : " + userId));
//        try{
//            user.setRatings(ratingService.getRatingByUserId(user.getUserId()));
//        }catch (Exception e){
//            user.setRatings(new ArrayList<>());
//        }
        user.setRatings(ratingService.getRatingByUserId(user.getUserId()));


        return user;
    }

    @Override
    public String removeUser(String userId) {
        userRepository.delete(
                userRepository.findById(userId).orElseThrow(() ->
                        new ResourceNotFoundException("User with Given Id is not found on server : " + userId))
        );
        return userId;
    }

    @Override
    public User updateUser(User user) {
        //TODO
        return null;
    }
}
