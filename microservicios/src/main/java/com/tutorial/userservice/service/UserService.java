package com.tutorial.userservice.service;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.feignclients.BikeFeignClient;
import com.tutorial.userservice.feignclients.CarFeignClient;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.repositorie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final String hostCar = "http://localhost:8082/car/";
    private final String hostBike = "http://localhost:8083/bike/";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CarFeignClient carFeignClient;

    @Autowired
    BikeFeignClient bikeFeignClient;
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

    public Car saveCar(int userId, Car car) {
        car.setUserId(userId);
        Car newCar = carFeignClient.save(car);
        return newCar;
    }

    public Bike saveBike(int userId, Bike bike) {
        bike.setUserId(userId);
        Bike newBike = bikeFeignClient.save(bike);
        return newBike;
    }

    public Map<String, Object> getUserAndVehicles(int userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            result.put("Mensaje", "No existe el usuario");
            return result;
        }

        result.put("User", user);
        List<Car> cars = carFeignClient.getCars(userId);
        if(cars.isEmpty()) {
            result.put("Cars", "El usuario no tiene carros");
        }
        else {
            result.put("Cars", cars);
        }

        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if(bikes.isEmpty()) {
            result.put("Bikes", "El usuario no tiene motos");
        }
        else {
            result.put("Bikes", bikes);
        }
        return result;
    }
}
