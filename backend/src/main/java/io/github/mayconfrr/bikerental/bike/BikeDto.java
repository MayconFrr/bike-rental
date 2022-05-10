package io.github.mayconfrr.bikerental.bike;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

public record BikeDto(
        @NotBlank(message = "Model must not be blank")
        String model,

        @NotBlank(message = "Color must not be blank")
        String color,

        @NotNull(message = "Price must not be null")
        @Positive(message = "Price must be greater than 0")
        Integer size,

        @NotNull(message = "Price must not be null")
        @Positive(message = "Price must be greater than 0")
        BigDecimal price

) implements Serializable {
}
