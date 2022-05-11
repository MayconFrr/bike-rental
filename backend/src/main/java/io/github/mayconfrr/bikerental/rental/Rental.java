package io.github.mayconfrr.bikerental.rental;

import io.github.mayconfrr.bikerental.bike.Bike;
import io.github.mayconfrr.bikerental.client.Client;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
    private Bike bike;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;
}
