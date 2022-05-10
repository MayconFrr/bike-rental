package io.github.mayconfrr.bikerental.bike;

public class BikeNotFoundException extends RuntimeException {
    public BikeNotFoundException(Long id) {
        super("Bike with id " + id + " not found");
    }
}
