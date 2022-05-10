package io.github.mayconfrr.bikerental.client;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 14, columnDefinition = "bpchar(14)")
    private String cpf;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 14, columnDefinition = "bpchar(14)")
    private String phone;
}
