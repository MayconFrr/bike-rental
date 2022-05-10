package io.github.mayconfrr.bikerental.bike;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String color;
    private Integer size;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bike bike = (Bike) o;
        return id != null && Objects.equals(id, bike.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
