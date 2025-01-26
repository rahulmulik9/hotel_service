package com.rahul.user_service.service.impl;


import com.rahul.user_service.entity.Hotel;
import com.rahul.user_service.entity.Rating;
import com.rahul.user_service.exception.ResourceNotFoundException;
import com.rahul.user_service.repository.UserRepository;
import com.rahul.user_service.entity.User;
import com.rahul.user_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;


    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomId= UUID.randomUUID().toString();
        user.setUserId(randomId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {

        User theUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        //get rating from user directly (require the bean of  Rest template object which is declared in the main class)
        //ArrayList ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/e1f229aa-abf5-47d6-9aba-c6ee012473b9", ArrayList.class);
        //Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+theUser.getUserId(), Rating[].class);

        //also fetching hotels data
        Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/" + theUser.getUserId(), Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel
            Hotel hotel = restTemplate.getForObject("http://localhost:8082/hotels/1b6b9408-1221-4bf6-9951-8faaa81a0f6a", Hotel.class);
            //Hotel hotel = hotelService.getHotel(rating.getHotelId());
            // logger.info("response status code: {} ",forEntity.getStatusCode());
            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());

        theUser.setRatings(Arrays.stream(ratingsOfUser).toList());
        logger.info("{} ", ratingsOfUser);
        return theUser;
    }

}