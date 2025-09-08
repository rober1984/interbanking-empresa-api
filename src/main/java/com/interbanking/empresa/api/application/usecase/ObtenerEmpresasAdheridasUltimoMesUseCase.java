package com.interbanking.empresa.api.application.usecase;

import com.interbanking.empresa.api.domain.model.Empresa;
import com.interbanking.empresa.api.domain.port.EmpresaRepositoryPort;
import com.interbanking.empresa.api.domain.vo.RangoMes;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObtenerEmpresasAdheridasUltimoMesUseCase {

    private final EmpresaRepositoryPort empresaRepositoryPort;

    public ObtenerEmpresasAdheridasUltimoMesUseCase(EmpresaRepositoryPort empresaRepositoryPort) {
        this.empresaRepositoryPort = empresaRepositoryPort;
    }

    public List<Empresa> ejecutar() {
        RangoMes rango = RangoMes.mesPasado();

        return empresaRepositoryPort.findByFechaAdhesionBetween(rango.inicio(), rango.fin());
    }
}
