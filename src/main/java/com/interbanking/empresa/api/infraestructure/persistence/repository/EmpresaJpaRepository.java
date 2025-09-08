package com.interbanking.empresa.api.infraestructure.persistence.repository;

import com.interbanking.empresa.api.infraestructure.persistence.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaJpaRepository extends JpaRepository<EmpresaEntity, Long> {
    Optional<EmpresaEntity> findByCuit(String cuit);

    List<EmpresaEntity> findByFechaAdhesionBetween(LocalDate desde, LocalDate hasta);
}

