package com.interbanking.empresa.api.controller;

import com.interbanking.empresa.api.application.usecase.AdhesionEmpresaUseCase;
import com.interbanking.empresa.api.application.usecase.ObtenerEmpresasAdheridasUltimoMesUseCase;
import com.interbanking.empresa.api.application.usecase.ObtenerEmpresasConTransferenciasUltimoMesUseCase;
import com.interbanking.empresa.api.application.usecase.dto.response.AdhesionEmpresaResponse;
import com.interbanking.empresa.api.controller.dto.response.EmpresaResponse;
import com.interbanking.empresa.api.controller.mapper.EmpresaResponseMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    private final AdhesionEmpresaUseCase adhesionEmpresaUseCase;
    private final ObtenerEmpresasAdheridasUltimoMesUseCase obtenerEmpresasAdheridasUltimoMesUseCase;
    private final ObtenerEmpresasConTransferenciasUltimoMesUseCase obtenerEmpresasConTransferenciasUltimoMesUseCase;

    public EmpresaController(AdhesionEmpresaUseCase adhesionEmpresaUseCase,
                             ObtenerEmpresasAdheridasUltimoMesUseCase obtenerEmpresasAdheridasUltimoMesUseCase,
                             ObtenerEmpresasConTransferenciasUltimoMesUseCase obtenerEmpresasConTransferenciasUltimoMesUseCase) {
        this.adhesionEmpresaUseCase = adhesionEmpresaUseCase;
        this.obtenerEmpresasAdheridasUltimoMesUseCase = obtenerEmpresasAdheridasUltimoMesUseCase;
        this.obtenerEmpresasConTransferenciasUltimoMesUseCase = obtenerEmpresasConTransferenciasUltimoMesUseCase;
    }

    @GetMapping("/transferencias/ultimo-mes")
    public ResponseEntity<List<EmpresaResponse>> getEmpresasConTransferenciasUltimoMes() {
        List<EmpresaResponse> empresas = obtenerEmpresasConTransferenciasUltimoMesUseCase.ejecutar().stream()
                .map(EmpresaResponseMapper::toResponse)
                .toList();

        return ResponseEntity.ok(empresas);

    }

    @GetMapping("/adhesiones/ultimo-mes")
    public ResponseEntity<List<EmpresaResponse>> getEmpresasAdheridasUltimoMes() {
        List<EmpresaResponse> empresas = obtenerEmpresasAdheridasUltimoMesUseCase.ejecutar().stream()
                .map(EmpresaResponseMapper::toResponse)
                .toList();

        return ResponseEntity.ok(empresas);
    }


    @PatchMapping("/{cuit}/adhesion")
    public ResponseEntity<String> adhesionEmpresa(@PathVariable String cuit) {

        AdhesionEmpresaResponse response = adhesionEmpresaUseCase.procesarAdhesion(cuit);

        if (!response.isSuccess()) {
            return ResponseEntity.status(response.getHttpStatus()).body(response.getMessage());
        }

        return ResponseEntity.ok().body(response.getMessage());
    }
}
