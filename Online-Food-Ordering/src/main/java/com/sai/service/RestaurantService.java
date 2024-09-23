package com.sai.service;

import com.sai.dto.RestaurantDTO;
import com.sai.model.Restaurant;
import com.sai.model.User;
import com.sai.request.CreateRestaurentRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurentRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurentRequest updateRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDTO addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;

}
