package io.github.mayconfrr.bikerental.rental;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Rentals")
@Slf4j
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<Rental> getRentals() {
        log.info("GET request for all Rentals");
        return rentalService.getRentals();
    }

    @GetMapping("{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable("id") Long id) {
        log.info("GET request for Rental with id: {}", id);
        return ResponseEntity.of(rentalService.getRentalById(id));
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@Valid @RequestBody RentalDto rentalDto) {
        log.info("POST request for Rental with DTO: {}", rentalDto);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .build(rentalService.saveRental(rentalDto).getId())
        ).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateRentalById(@PathVariable("id") Long id, @Valid @RequestBody RentalDto rentalDto) {
        log.info("PUT request for Rental with id: {} and DTO: {}", id, rentalDto);
        rentalService.updateRentalById(id, rentalDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRentalById(@PathVariable("id") Long id) {
        log.info("DELETE request for Rental with id: {}", id);
        rentalService.deleteRentalById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<Void> handleRentalNotFound(RentalNotFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity.notFound().build();
    }
}
