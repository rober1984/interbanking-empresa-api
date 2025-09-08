package com.interbanking.empresa.api.infraestructure.persistence.repository;

import com.interbanking.empresa.api.infraestructure.persistence.entity.EmpresaEntity;
import com.interbanking.empresa.api.infraestructure.persistence.entity.TransferenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransferenciaJpaRepository extends JpaRepository<TransferenciaEntity, Long> {
    @Query("SELECT DISTINCT t.empresa FROM TransferenciaEntity t " +
            "WHERE t.fechaTransferencia BETWEEN :desde AND :hasta")
    List<EmpresaEntity> findEmpresasConTransferenciasEntre(
            @Param("desde") LocalDate inicioMesPasado,
            @Param("hasta") LocalDate finMesPasado);
}

