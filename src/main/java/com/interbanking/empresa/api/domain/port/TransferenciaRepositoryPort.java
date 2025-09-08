package com.interbanking.empresa.api.domain.port;

import com.interbanking.empresa.api.domain.model.Empresa;

import java.time.LocalDate;
import java.util.List;

public interface TransferenciaRepositoryPort {
    List<Empresa> findEmpresasConTransferenciasEntre(LocalDate desde, LocalDate hasta);
}
