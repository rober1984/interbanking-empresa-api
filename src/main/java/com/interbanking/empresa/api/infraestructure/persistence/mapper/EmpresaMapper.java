package com.interbanking.empresa.api.infraestructure.persistence.mapper;

import com.interbanking.empresa.api.domain.model.Empresa;
import com.interbanking.empresa.api.infraestructure.persistence.entity.EmpresaEntity;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {

    public Empresa toDomain(EmpresaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Empresa.builder()
                .id(entity.getId())
                .cuit(entity.getCuit())
                .razonSocial(entity.getRazonSocial())
                .fechaAdhesion(entity.getFechaAdhesion())
                .build();
    }

    public EmpresaEntity toEntity(Empresa domain) {
        if (domain == null) {
            return null;
        }
        
        return EmpresaEntity.builder()
                .id(domain.getId())
                .cuit(domain.getCuit())
                .razonSocial(domain.getRazonSocial())
                .fechaAdhesion(domain.getFechaAdhesion())
                .build();
    }
}

