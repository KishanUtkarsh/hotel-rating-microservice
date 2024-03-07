package com.github.userservice.controllers;


import com.github.userservice.entities.User;
import com.github.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService service;

    private Logger logger = (Logger) LoggerFactory.getLogger(UserRestController.class);

    //creating user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        User user1 = service.saveUser(user);

        // we can use this also { return ResponseEntity.status(HttpStatus.CREATED).body(user1); }
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    // sending single user data
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "RatingCircuitBreaker",fallbackMethod = "ratingFault")
    public ResponseEntity<User> getUser(@PathVariable String userId){

        User user = service.getUser(userId);

        return ResponseEntity.ok(user);
    }

    //Fallback method for circuit breaker for sending response

    public ResponseEntity<User> ratingFault(String userId,Exception ex){
        logger.info("Rating Server is Down : Ex : {}", ex.getMessage());
        User user =  new User();
        user.setUserId(userId);
        return ResponseEntity.ok(user);
    }


    //sending all data of user
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){

        List<User> users = service.getAllUser();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId){

        String userId1 = service.removeUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(userId1+" Deleted");
    }

    @PostMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user){

        User user1 = service.updateUser(user);

        return ResponseEntity.ok(user1);
    }
}
