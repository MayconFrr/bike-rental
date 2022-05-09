package io.github.mayconfrr.bikerental.bike;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BikeService {
    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public List<Bike> getBikes() {
        return bikeRepository.findAll();
    }

    public Optional<Bike> getBikeById(Long id) {
        return bikeRepository.findById(id);
    }

    public Bike saveBike(BikeDto bikeDto) {
        Bike bike = Bike.builder()
                .model(bikeDto.model())
                .color(bikeDto.color())
                .price(bikeDto.price())
                .size(bikeDto.size())
                .build();

        return bikeRepository.save(bike);
    }

    public void updateBikeById(Long id, BikeDto bikeDto) {
        Bike updatedBike = bikeRepository.findById(id)
                .map(bike -> bike.withModel(bikeDto.model()))
                .map(bike -> bike.withColor(bikeDto.color()))
                .map(bike -> bike.withSize(bikeDto.size()))
                .map(bike -> bike.withPrice(bikeDto.price()))
                .orElseThrow(() -> new BikeNotFoundException(id));

        bikeRepository.save(updatedBike);
    }

    @Transactional
    public void deleteBikeById(Long id) {
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new BikeNotFoundException(id));
        bikeRepository.delete(bike);
    }
}
