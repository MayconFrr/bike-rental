package io.github.mayconfrr.bikerental.rental;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public record RentalDto(

        @NotNull(message = "Client ID is required")
        Long clientId,

        @NotNull(message = "Bike ID is required")
        Long bikeId,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        LocalDate endDate

) implements Serializable {
}
