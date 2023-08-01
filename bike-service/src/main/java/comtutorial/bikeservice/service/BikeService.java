package comtutorial.bikeservice.service;

import comtutorial.bikeservice.entity.Bike;
import comtutorial.bikeservice.repositorie.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {
    @Autowired
    private BikeRepository bikeRepository;

    public List<Bike> getAll() {
        return bikeRepository.findAll();
    }

    public Bike getBikeById(int id) {
        return bikeRepository.findById(id).orElse(null);
    }

    public Bike save(Bike bike) {
        Bike newBike = bikeRepository.save(bike);
        return newBike;
    }

    public List<Bike> findByUserId(int userId) {
        return bikeRepository.findByUserId(userId);
    }
}
