package com.github.hotel.service;

import com.github.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel addHotel(Hotel hotel);

    Hotel getHotel(String hotelId);

    List<Hotel> getAllHotel();

    String removeHotel(String hotelId);

    Hotel updateHotel(Hotel hotel);
}
