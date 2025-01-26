package com.rahul.hotel_service.repository;
import com.rahul.hotel_service.enitity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}
