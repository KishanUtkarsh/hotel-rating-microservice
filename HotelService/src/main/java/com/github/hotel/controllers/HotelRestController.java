package com.github.hotel.controllers;

import com.github.hotel.entities.Hotel;
import com.github.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelRestController {

    @Autowired
    private HotelService service;

    @PostMapping
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel){

        Hotel hotel1 = service.addHotel(hotel);

        return new ResponseEntity<Hotel>(hotel1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotel(){
        List<Hotel> hotels = service.getAllHotel();

        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotel(@PathVariable String hotelId){
        Hotel hotel = service.getHotel(hotelId);

        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/remove/{hotelId}")
    public ResponseEntity<String> removeHotel(@PathVariable String hotelId){
        String hotelId1 = service.removeHotel(hotelId);

        return ResponseEntity.ok(hotelId+" removed");
    }

    @GetMapping("/update")
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel){
        return ResponseEntity.ok(service.updateHotel(hotel));
    }
}
