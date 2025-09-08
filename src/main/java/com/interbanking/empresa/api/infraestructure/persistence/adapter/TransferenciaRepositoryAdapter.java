package com.interbanking.empresa.api.infraestructure.persistence.adapter;

import com.interbanking.empresa.api.domain.model.Empresa;
import com.interbanking.empresa.api.domain.port.TransferenciaRepositoryPort;
import com.interbanking.empresa.api.infraestructure.persistence.entity.EmpresaEntity;
import com.interbanking.empresa.api.infraestructure.persistence.mapper.EmpresaMapper;
import com.interbanking.empresa.api.infraestructure.persistence.repository.TransferenciaJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TransferenciaRepositoryAdapter implements TransferenciaRepositoryPort {

    private final TransferenciaJpaRepository transferenciaJpaRepository;
    private final EmpresaMapper empresaMapper;

    public TransferenciaRepositoryAdapter(TransferenciaJpaRepository transferenciaJpaRepository, EmpresaMapper empresaMapper) {
        this.transferenciaJpaRepository = transferenciaJpaRepository;
        this.empresaMapper = empresaMapper;
    }

    @Override
    public List<Empresa> findEmpresasConTransferenciasEntre(LocalDate inicioMesPasado, LocalDate finMesPasado) {
        List<EmpresaEntity> entities = transferenciaJpaRepository.findEmpresasConTransferenciasEntre(inicioMesPasado, finMesPasado);
        return entities.stream()
                .map(empresaMapper::toDomain)
                .toList();
    }
}

