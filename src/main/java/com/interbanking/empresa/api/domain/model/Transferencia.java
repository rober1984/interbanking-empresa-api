package com.interbanking.empresa.api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Transferencia {
    private Long id;
    private Empresa empresa;
    private BigDecimal importe;
    private String cuentaDebito;
    private String cuentaCredito;
    private LocalDate fechaTransferencia;

    public Transferencia() {
    }

    public Transferencia(Long id, Empresa empresa, BigDecimal importe, String cuentaDebito, String cuentaCredito, LocalDate fechaTransferencia) {
        this.id = id;
        this.empresa = empresa;
        this.importe = importe;
        this.cuentaDebito = cuentaDebito;
        this.cuentaCredito = cuentaCredito;
        this.fechaTransferencia = fechaTransferencia;
        validateBusinessRules();
    }

    /**
     * Valida las reglas de negocio de la transferencia
     */
    private void validateBusinessRules() {
        validateEmpresa();
        validateImporte();
        validateCuentas();
        validateFechaTransferencia();
    }

    /**
     * Valida que la empresa no sea nula
     */
    private void validateEmpresa() {
        if (empresa == null) {
            throw new IllegalArgumentException("La empresa no puede ser nula");
        }
    }

    /**
     * Valida que el importe sea válido
     */
    private void validateImporte() {
        if (importe == null) {
            throw new IllegalArgumentException("El importe no puede ser nulo");
        }
        if (importe.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor a cero");
        }
        if (importe.scale() > 2) {
            throw new IllegalArgumentException("El importe no puede tener más de 2 decimales");
        }
    }

    /**
     * Valida que las cuentas sean válidas
     */
    private void validateCuentas() {
        validateCuentaDebito();
        validateCuentaCredito();
        validateCuentasDiferentes();
    }

    private void validateCuentaDebito() {
        if (cuentaDebito == null || cuentaDebito.trim().isEmpty()) {
            throw new IllegalArgumentException("La cuenta débito no puede estar vacía");
        }
        if (cuentaDebito.length() > 34) {
            throw new IllegalArgumentException("La cuenta débito no puede exceder 34 caracteres");
        }
    }

    private void validateCuentaCredito() {
        if (cuentaCredito == null || cuentaCredito.trim().isEmpty()) {
            throw new IllegalArgumentException("La cuenta crédito no puede estar vacía");
        }
        if (cuentaCredito.length() > 34) {
            throw new IllegalArgumentException("La cuenta crédito no puede exceder 34 caracteres");
        }
    }

    private void validateCuentasDiferentes() {
        if (cuentaDebito.equals(cuentaCredito)) {
            throw new IllegalArgumentException("Las cuentas débito y crédito deben ser diferentes");
        }
    }

    /**
     * Valida que la fecha de transferencia sea válida
     */
    private void validateFechaTransferencia() {
        if (fechaTransferencia == null) {
            throw new IllegalArgumentException("La fecha de transferencia no puede ser nula");
        }
        if (fechaTransferencia.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de transferencia no puede ser futura");
        }
    }

    public static TransferenciaBuilder builder() {
        return new TransferenciaBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
        validateEmpresa();
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
        validateImporte();
    }

    public String getCuentaDebito() {
        return cuentaDebito;
    }

    public void setCuentaDebito(String cuentaDebito) {
        this.cuentaDebito = cuentaDebito;
        validateCuentaDebito();
    }

    public String getCuentaCredito() {
        return cuentaCredito;
    }

    public void setCuentaCredito(String cuentaCredito) {
        this.cuentaCredito = cuentaCredito;
        validateCuentaCredito();
    }

    public LocalDate getFechaTransferencia() {
        return fechaTransferencia;
    }

    public void setFechaTransferencia(LocalDate fechaTransferencia) {
        this.fechaTransferencia = fechaTransferencia;
        validateFechaTransferencia();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transferencia that = (Transferencia) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transferencia{" +
                "id=" + id +
                ", empresa=" + empresa +
                ", importe=" + importe +
                ", cuentaDebito='" + cuentaDebito + '\'' +
                ", cuentaCredito='" + cuentaCredito + '\'' +
                ", fechaTransferencia=" + fechaTransferencia +
                '}';
    }

    public static class TransferenciaBuilder {
        private Long id;
        private Empresa empresa;
        private BigDecimal importe;
        private String cuentaDebito;
        private String cuentaCredito;
        private LocalDate fechaTransferencia;

        public TransferenciaBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TransferenciaBuilder empresa(Empresa empresa) {
            this.empresa = empresa;
            return this;
        }

        public TransferenciaBuilder importe(BigDecimal importe) {
            this.importe = importe;
            return this;
        }

        public TransferenciaBuilder cuentaDebito(String cuentaDebito) {
            this.cuentaDebito = cuentaDebito;
            return this;
        }

        public TransferenciaBuilder cuentaCredito(String cuentaCredito) {
            this.cuentaCredito = cuentaCredito;
            return this;
        }

        public TransferenciaBuilder fechaTransferencia(LocalDate fechaTransferencia) {
            this.fechaTransferencia = fechaTransferencia;
            return this;
        }

        public Transferencia build() {
            return new Transferencia(id, empresa, importe, cuentaDebito, cuentaCredito, fechaTransferencia);
        }
    }
}
