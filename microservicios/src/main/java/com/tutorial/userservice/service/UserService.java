package com.tutorial.userservice.service;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.repositorie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    private final String hostCar = "http://localhost:8082/car/";
    private final String hostBike = "http://localhost:8083/bike/";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        User userNew = userRepository.save(user);
        return userNew;
    }

    public List<Car> getCars(int id) {
        String url = hostCar.concat("byUser/") + id;
        List<Car> cars = restTemplate.getForObject(url, List.class);
        return cars;
    }

    public List<Bike> getBikes(int id) {
        String url = hostBike.concat("byUser/") + id;
        List<Bike> bikes = restTemplate.getForObject(url, List.class);
        return bikes;
    }
}
