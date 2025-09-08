package com.interbanking.empresa.api.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String cuit;

    @Column(nullable = false, length = 255)
    private String razonSocial;

    @Column(name = "fecha_adhesion")
    private LocalDate fechaAdhesion;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<TransferenciaEntity> transferencias = new ArrayList<>();
}
