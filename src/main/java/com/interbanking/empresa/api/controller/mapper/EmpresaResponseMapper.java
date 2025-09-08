package com.interbanking.empresa.api.controller.mapper;

import com.interbanking.empresa.api.controller.dto.response.EmpresaResponse;
import com.interbanking.empresa.api.domain.model.Empresa;
import org.springframework.stereotype.Component;

@Component
public class EmpresaResponseMapper {

    public static EmpresaResponse toResponse(Empresa e) {
        return new EmpresaResponse(e.getId(), e.getCuit(), e.getRazonSocial(), e.getFechaAdhesion());
    }
}
