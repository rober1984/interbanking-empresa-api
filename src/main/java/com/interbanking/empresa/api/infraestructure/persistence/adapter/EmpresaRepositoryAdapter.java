package com.interbanking.empresa.api.infraestructure.persistence.adapter;

import com.interbanking.empresa.api.domain.model.Empresa;
import com.interbanking.empresa.api.domain.port.EmpresaRepositoryPort;
import com.interbanking.empresa.api.infraestructure.persistence.entity.EmpresaEntity;
import com.interbanking.empresa.api.infraestructure.persistence.mapper.EmpresaMapper;
import com.interbanking.empresa.api.infraestructure.persistence.repository.EmpresaJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class EmpresaRepositoryAdapter implements EmpresaRepositoryPort {

    private final EmpresaJpaRepository empresaJpaRepository;
    private final EmpresaMapper empresaMapper;

    public EmpresaRepositoryAdapter(EmpresaJpaRepository empresaJpaRepository, EmpresaMapper empresaMapper) {
        this.empresaJpaRepository = empresaJpaRepository;
        this.empresaMapper = empresaMapper;
    }

    @Override
    public Empresa save(Empresa empresa) {
        EmpresaEntity entity = empresaMapper.toEntity(empresa);
        EmpresaEntity savedEntity = empresaJpaRepository.save(entity);
        return empresaMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Empresa> findByCuit(String cuit) {
        return empresaJpaRepository.findByCuit(cuit)
                .map(empresaMapper::toDomain);
    }

    @Override
    public List<Empresa> findByFechaAdhesionBetween(LocalDate desde, LocalDate hasta) {
        List<EmpresaEntity> entities = empresaJpaRepository.findByFechaAdhesionBetween(desde, hasta);
        return entities.stream()
                .map(empresaMapper::toDomain)
                .toList();
    }
}
