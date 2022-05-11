package io.github.mayconfrr.bikerental.rental;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException(Long id) {
        super("Rental with id " + id + " not found");
    }
}
