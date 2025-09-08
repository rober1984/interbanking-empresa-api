package com.interbanking.empresa.api.domain.port;

import com.interbanking.empresa.api.domain.model.Empresa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmpresaRepositoryPort {
    Empresa save(Empresa empresa);

    Optional<Empresa> findByCuit(String cuit);

    List<Empresa> findByFechaAdhesionBetween(LocalDate inicio, LocalDate fin);
}
