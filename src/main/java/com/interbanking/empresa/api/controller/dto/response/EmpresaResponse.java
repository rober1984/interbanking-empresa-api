package com.interbanking.empresa.api.controller.dto.response;

import java.time.LocalDate;

public record EmpresaResponse(
        Long id,
        String cuit,
        String razonSocial,
        LocalDate fechaAdhesion
) {}
