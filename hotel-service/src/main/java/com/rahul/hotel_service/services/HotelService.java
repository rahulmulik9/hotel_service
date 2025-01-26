package com.rahul.hotel_service.services;


import com.rahul.hotel_service.enitity.Hotel;

import java.util.List;

public interface HotelService {

    //create

    Hotel create(Hotel hotel);

    //get all
    List<Hotel> getAll();

    //get single
    Hotel get(String id);

}