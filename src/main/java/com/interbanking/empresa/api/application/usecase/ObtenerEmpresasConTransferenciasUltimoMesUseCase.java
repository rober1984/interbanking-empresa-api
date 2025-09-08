package com.interbanking.empresa.api.application.usecase;

import com.interbanking.empresa.api.domain.model.Empresa;
import com.interbanking.empresa.api.domain.port.TransferenciaRepositoryPort;
import com.interbanking.empresa.api.domain.vo.RangoMes;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObtenerEmpresasConTransferenciasUltimoMesUseCase {
    private final TransferenciaRepositoryPort transferenciaRepositoryPort;

    public ObtenerEmpresasConTransferenciasUltimoMesUseCase(TransferenciaRepositoryPort transferenciaRepositoryPort) {
        this.transferenciaRepositoryPort = transferenciaRepositoryPort;
    }

    public List<Empresa> ejecutar() {
        RangoMes rango = RangoMes.mesPasado();

        List<Empresa> empresas = transferenciaRepositoryPort.findEmpresasConTransferenciasEntre(rango.inicio(), rango.fin());

        return empresas.stream().toList();
    }
}
