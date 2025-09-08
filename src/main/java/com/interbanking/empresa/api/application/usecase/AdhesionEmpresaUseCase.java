package com.interbanking.empresa.api.application.usecase;

import com.interbanking.empresa.api.application.usecase.dto.response.AdhesionEmpresaDto;
import com.interbanking.empresa.api.application.usecase.dto.response.AdhesionEmpresaResponse;
import com.interbanking.empresa.api.domain.model.Empresa;
import com.interbanking.empresa.api.domain.port.EmpresaRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class AdhesionEmpresaUseCase {

    private final EmpresaRepositoryPort empresaRepositoryPort;

    public AdhesionEmpresaUseCase(EmpresaRepositoryPort empresaRepositoryPort) {
        this.empresaRepositoryPort = empresaRepositoryPort;
    }

    @Transactional
    public AdhesionEmpresaResponse procesarAdhesion(String cuit) {
        log.info("Iniciando proceso de adhesión para empresa con CUIT: {}", cuit);

        return empresaRepositoryPort.findByCuit(cuit)
                .map(empresa -> {
                    try {
                        log.debug("Empresa encontrada: {} - {}", empresa.getCuit(), empresa.getRazonSocial());
                        empresa.adherir();
                        Empresa empresaActualizada = empresaRepositoryPort.save(empresa);
                        AdhesionEmpresaDto dto = mapToDto(empresaActualizada);
                        log.info("Adhesión exitosa para empresa CUIT: {} - Fecha: {}",
                                empresaActualizada.getCuit(), empresaActualizada.getFechaAdhesion());
                        return AdhesionEmpresaResponse.success(dto);
                    } catch (IllegalStateException e) {
                        log.warn("Intento de adhesión fallido - Empresa ya adherida CUIT: {} - Error: {}",
                                empresa.getCuit(), e.getMessage());
                        return AdhesionEmpresaResponse.alreadyAdhered(e.getMessage());
                    }
                })
                .orElseGet(() -> {
                    log.warn("Empresa no encontrada con CUIT: {}", cuit);
                    return AdhesionEmpresaResponse.notFound("La empresa con CUIT " + cuit + " no existe");
                });
    }

    private AdhesionEmpresaDto mapToDto(Empresa empresa) {
        return new AdhesionEmpresaDto(
                empresa.getId(),
                empresa.getCuit(),
                empresa.getRazonSocial(),
                empresa.getFechaAdhesion()
        );
    }
}
