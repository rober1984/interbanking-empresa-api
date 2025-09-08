package com.interbanking.empresa.api.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public class Empresa {
    private Long id;
    private String cuit;
    private String razonSocial;
    private LocalDate fechaAdhesion;

    public Empresa() {}

    public Empresa(Long id, String cuit, String razonSocial, LocalDate fechaAdhesion) {
        this.id = id;
        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.fechaAdhesion = fechaAdhesion;
        validateBusinessRules();
    }

    public static EmpresaBuilder builder() {
        return new EmpresaBuilder();
    }

    /**
     * Valida las reglas de negocio de la empresa
     */
    private void validateBusinessRules() {
        validateCuit();
        validateRazonSocial();
    }

    /**
     * Valida que el CUIT tenga el formato correcto (11 dígitos)
     */
    private void validateCuit() {
        if (cuit == null || cuit.trim().isEmpty()) {
            throw new IllegalArgumentException("El CUIT no puede estar vacío");
        }
        if (!cuit.matches("\\d{11}")) {
            throw new IllegalArgumentException("El CUIT debe tener exactamente 11 dígitos");
        }
    }

    /**
     * Valida que la razón social no esté vacía
     */
    private void validateRazonSocial() {
        if (razonSocial == null || razonSocial.trim().isEmpty()) {
            throw new IllegalArgumentException("La razón social no puede estar vacía");
        }
        if (razonSocial.length() > 255) {
            throw new IllegalArgumentException("La razón social no puede exceder 255 caracteres");
        }
    }

    /**
     * Marca la empresa como adherida
     */
    public void adherir() {
        if (fechaAdhesion != null) {
            throw new IllegalStateException("La empresa ya está adherida desde " + fechaAdhesion);
        }
        this.fechaAdhesion = LocalDate.now();
    }

    /**
     * Verifica si la empresa está adherida
     */
    public boolean estaAdherida() {
        return fechaAdhesion != null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
        validateCuit();
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        validateRazonSocial();
    }

    public LocalDate getFechaAdhesion() {
        return fechaAdhesion;
    }

    public void setFechaAdhesion(LocalDate fechaAdhesion) {
        this.fechaAdhesion = fechaAdhesion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(id, empresa.id) && Objects.equals(cuit, empresa.cuit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cuit);
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", cuit='" + cuit + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", fechaAdhesion=" + fechaAdhesion +
                '}';
    }

    public static class EmpresaBuilder {
        private Long id;
        private String cuit;
        private String razonSocial;
        private LocalDate fechaAdhesion;

        public EmpresaBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public EmpresaBuilder cuit(String cuit) {
            this.cuit = cuit;
            return this;
        }

        public EmpresaBuilder razonSocial(String razonSocial) {
            this.razonSocial = razonSocial;
            return this;
        }

        public EmpresaBuilder fechaAdhesion(LocalDate fechaAdhesion) {
            this.fechaAdhesion = fechaAdhesion;
            return this;
        }

        public Empresa build() {
            return new Empresa(id, cuit, razonSocial, fechaAdhesion);
        }
    }
}
