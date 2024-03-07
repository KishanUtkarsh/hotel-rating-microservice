package com.github.hotel.service.impl;

import com.github.hotel.entities.Hotel;
import com.github.hotel.exceptions.ResourceNotFoundException;
import com.github.hotel.repositories.HotelRepository;
import com.github.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel addHotel(Hotel hotel) {

        String hotelId = UUID.randomUUID().toString();
        hotel.setHotelId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotel(String hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with given id is not found : "+hotelId));
    }

    @Override
    public List<Hotel> getAllHotel() {
        return hotelRepository.findAll();
    }

    @Override
    public String removeHotel(String hotelId) {
        hotelRepository.delete(
                hotelRepository.findById(hotelId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException
                                        ("Hotel with given id is not found : "+hotelId)
        ));
        return hotelId;
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
        return null;
    }
}
