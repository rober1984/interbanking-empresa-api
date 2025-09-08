package com.interbanking.empresa.api.application.usecase.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AdhesionEmpresaResponse {

    private final boolean success;
    private final String message;
    private final AdhesionEmpresaDto empresa;
    private final HttpStatus httpStatus;

    private AdhesionEmpresaResponse(boolean success, String message, AdhesionEmpresaDto empresa, HttpStatus httpStatus) {
        this.success = success;
        this.message = message;
        this.empresa = empresa;
        this.httpStatus = httpStatus;
    }

    public static AdhesionEmpresaResponse success(AdhesionEmpresaDto empresa) {
        return new AdhesionEmpresaResponse(true, "Empresa adherida correctamente", empresa, HttpStatus.OK);
    }

    public static AdhesionEmpresaResponse notFound(String message) {
        return new AdhesionEmpresaResponse(false, message, null,  HttpStatus.NOT_FOUND);
    }

    public static AdhesionEmpresaResponse alreadyAdhered(String message) {
        return new AdhesionEmpresaResponse(false, message, null, HttpStatus.CONFLICT);
    }
}

