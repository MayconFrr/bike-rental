package io.github.mayconfrr.bikerental.bike;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping("/bikes")
@Slf4j
public class BikeController {
    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping
    public List<Bike> getBikes() {
        log.info("GET request for all bikes");
        return bikeService.getBikes();
    }

    @GetMapping("{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable("id") Long id) {
        log.info("GET request for bike with id: {}", id);
        return ResponseEntity.of(bikeService.getBikeById(id));
    }

    @PostMapping
    public ResponseEntity<Bike> createBike(@Valid @RequestBody BikeDto bikeDto) {
        log.info("POST request for bike with DTO: {}", bikeDto);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .build(bikeService.saveBike(bikeDto).getId())
        ).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateBikeByIdd(@PathVariable("id") Long id, @Valid @RequestBody BikeDto bikeDto) {
        log.info("PUT request for bike with id: {} and DTO: {}", id, bikeDto);
        bikeService.updateBikeById(id, bikeDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBikeById(@PathVariable("id") Long id) {
        log.info("DELETE request for bike with id: {}", id);
        bikeService.deleteBikeById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BikeNotFoundException.class)
    public ResponseEntity<Void> handleBikeNotFound(BikeNotFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        log.info("Validation exception: {}", e.getMessage());
        Map<String, String> errors = e.getBindingResult().getAllErrors()
                .stream()
                .map(FieldError.class::cast)
                .collect(toMap(
                        FieldError::getField,
                        error -> Objects.requireNonNullElse(error.getDefaultMessage(), "Unknown error")
                ));

        return ResponseEntity.badRequest().body(errors);
    }
}
