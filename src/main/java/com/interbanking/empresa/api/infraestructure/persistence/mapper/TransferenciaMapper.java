package com.interbanking.empresa.api.infraestructure.persistence.mapper;

import com.interbanking.empresa.api.domain.model.Empresa;
import com.interbanking.empresa.api.domain.model.Transferencia;
import com.interbanking.empresa.api.infraestructure.persistence.entity.EmpresaEntity;
import com.interbanking.empresa.api.infraestructure.persistence.entity.TransferenciaEntity;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaMapper {

    private final EmpresaMapper empresaMapper;

    public TransferenciaMapper(EmpresaMapper empresaMapper) {
        this.empresaMapper = empresaMapper;
    }

    public Transferencia toDomain(TransferenciaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Empresa empresa = empresaMapper.toDomain(entity.getEmpresa());
        
        return Transferencia.builder()
                .id(entity.getId())
                .empresa(empresa)
                .importe(entity.getImporte())
                .cuentaDebito(entity.getCuentaDebito())
                .cuentaCredito(entity.getCuentaCredito())
                .fechaTransferencia(entity.getFechaTransferencia())
                .build();
    }

    public TransferenciaEntity toEntity(Transferencia domain) {
        if (domain == null) {
            return null;
        }
        
        EmpresaEntity empresaEntity = empresaMapper.toEntity(domain.getEmpresa());
        
        return TransferenciaEntity.builder()
                .id(domain.getId())
                .empresa(empresaEntity)
                .importe(domain.getImporte())
                .cuentaDebito(domain.getCuentaDebito())
                .cuentaCredito(domain.getCuentaCredito())
                .fechaTransferencia(domain.getFechaTransferencia())
                .build();
    }
}

