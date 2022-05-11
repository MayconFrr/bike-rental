package io.github.mayconfrr.bikerental.rental;

import io.github.mayconfrr.bikerental.bike.Bike;
import io.github.mayconfrr.bikerental.bike.BikeNotFoundException;
import io.github.mayconfrr.bikerental.bike.BikeRepository;
import io.github.mayconfrr.bikerental.client.Client;
import io.github.mayconfrr.bikerental.client.ClientNotFoundException;
import io.github.mayconfrr.bikerental.client.ClientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    private final BikeRepository bikeRepository;
    private final ClientRepository clientRepository;
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository, ClientRepository clientRepository, BikeRepository bikeRepository) {
        this.rentalRepository = rentalRepository;
        this.clientRepository = clientRepository;
        this.bikeRepository = bikeRepository;
    }

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    @Transactional
    public Rental saveRental(RentalDto rentalDto) {
        Client client = clientRepository.findById(rentalDto.clientId())
                .orElseThrow(() -> new ClientNotFoundException(rentalDto.clientId()));
        Bike bike = bikeRepository.findById(rentalDto.bikeId())
                .orElseThrow(() -> new BikeNotFoundException(rentalDto.bikeId()));

        Rental rental = Rental.builder()
                .client(client)
                .bike(bike)
                .startDate(rentalDto.startDate())
                .endDate(rentalDto.endDate())
                .build();

        return rentalRepository.save(rental);
    }

    @Transactional
    public void updateRentalById(Long id, RentalDto rentalDto) {
        Client client = clientRepository.findById(rentalDto.clientId())
                .orElseThrow(() -> new ClientNotFoundException(rentalDto.clientId()));
        Bike bike = bikeRepository.findById(rentalDto.bikeId())
                .orElseThrow(() -> new BikeNotFoundException(rentalDto.bikeId()));

        Rental updatedRental = rentalRepository.findById(id)
                .map(rental -> rental.withClient(client))
                .map(rental -> rental.withBike(bike))
                .map(rental -> rental.withStartDate(rentalDto.startDate()))
                .map(rental -> rental.withEndDate(rentalDto.endDate()))
                .orElseThrow(() -> new RentalNotFoundException(id));

        rentalRepository.save(updatedRental);
    }

    public void deleteRentalById(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RentalNotFoundException(id));
        rentalRepository.delete(rental);
    }
}
