package com.interbanking.empresa.api.application.usecase.dto.response;

import java.time.LocalDate;

public record AdhesionEmpresaDto(
        Long id,
        String cuit,
        String razonSocial,
        LocalDate fechaAdhesion
) {}
