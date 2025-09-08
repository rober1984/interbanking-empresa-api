package com.interbanking.empresa.api.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Transferencia Domain Model Tests")
class TransferenciaTest {

    private Empresa createValidEmpresa() {
        return Empresa.builder()
                .id(1L)
                .cuit("20123456789")
                .razonSocial("Empresa Test S.A.")
                .build();
    }

    @Nested
    @DisplayName("Constructor Validation Tests")
    class ConstructorValidationTests {

        @Test
        @DisplayName("Debe crear una transferencia con datos válidos\n")
        void shouldCreateTransferenciaWithValidData() {
            // Given
            Long id = 1L;
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.50");
            String cuentaDebito = "1234567890123456789012345678901234";
            String cuentaCredito = "9876543210987654321098765432109876";
            LocalDate fechaTransferencia = LocalDate.now();

            // When
            Transferencia transferencia = new Transferencia(id, empresa, importe, cuentaDebito, cuentaCredito, fechaTransferencia);

            // Then
            assertNotNull(transferencia);
            assertEquals(id, transferencia.getId());
            assertEquals(empresa, transferencia.getEmpresa());
            assertEquals(importe, transferencia.getImporte());
            assertEquals(cuentaDebito, transferencia.getCuentaDebito());
            assertEquals(cuentaCredito, transferencia.getCuentaCredito());
            assertEquals(fechaTransferencia, transferencia.getFechaTransferencia());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando empresa sea nula")
        void shouldThrowExceptionWhenEmpresaIsNull() {
            // Given
            BigDecimal importe = new BigDecimal("1000.50");
            String cuentaDebito = "1234567890123456789012345678901234";
            String cuentaCredito = "9876543210987654321098765432109876";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, null, importe, cuentaDebito, cuentaCredito, fechaTransferencia)
            );
            assertEquals("La empresa no puede ser nula", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la importación sea nula")
        void shouldThrowExceptionWhenImporteIsNull() {
            // Given
            Empresa empresa = createValidEmpresa();
            String cuentaDebito = "1234567890123456789012345678901234";
            String cuentaCredito = "9876543210987654321098765432109876";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, null, cuentaDebito, cuentaCredito, fechaTransferencia)
            );
            assertEquals("El importe no puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la importación sea cero")
        void shouldThrowExceptionWhenImporteIsZero() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = BigDecimal.ZERO;
            String cuentaDebito = "1234567890123456789012345678901234";
            String cuentaCredito = "9876543210987654321098765432109876";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuentaDebito, cuentaCredito, fechaTransferencia)
            );
            assertEquals("El importe debe ser mayor a cero", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la importación sea negativa")
        void shouldThrowExceptionWhenImporteIsNegative() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("-100.00");
            String cuentaDebito = "1234567890123456789012345678901234";
            String cuentaCredito = "9876543210987654321098765432109876";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuentaDebito, cuentaCredito, fechaTransferencia)
            );
            assertEquals("El importe debe ser mayor a cero", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la importación tenga más de 2 decimales")
        void shouldThrowExceptionWhenImporteHasMoreThanTwoDecimals() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.123"); // 3 decimals
            String cuentaDebito = "1234567890123456789012345678901234";
            String cuentaCredito = "9876543210987654321098765432109876";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuentaDebito, cuentaCredito, fechaTransferencia)
            );
            assertEquals("El importe no puede tener más de 2 decimales", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la cuenta débito sea nula")
        void shouldThrowExceptionWhenCuentaDebitoIsNull() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.50");
            String cuentaCredito = "9876543210987654321098765432109876";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, null, cuentaCredito, fechaTransferencia)
            );
            assertEquals("La cuenta débito no puede estar vacía", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la cuenta débito esté vacía")
        void shouldThrowExceptionWhenCuentaDebitoIsEmpty() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.50");
            String cuentaDebito = "";
            String cuentaCredito = "9876543210987654321098765432109876";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuentaDebito, cuentaCredito, fechaTransferencia)
            );
            assertEquals("La cuenta débito no puede estar vacía", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la cuenta débito exceda la longitud máxima")
        void shouldThrowExceptionWhenCuentaDebitoExceedsMaxLength() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.50");
            String cuentaDebito = "A".repeat(35); // 35 characters
            String cuentaCredito = "9876543210987654321098765432109876";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuentaDebito, cuentaCredito, fechaTransferencia)
            );
            assertEquals("La cuenta débito no puede exceder 34 caracteres", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la cuenta de crédito sea nula")
        void shouldThrowExceptionWhenCuentaCreditoIsNull() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.50");
            String cuentaDebito = "1234567890123456789012345678901234";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuentaDebito, null, fechaTransferencia)
            );
            assertEquals("La cuenta crédito no puede estar vacía", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la cuenta de crédito esté vacía")
        void shouldThrowExceptionWhenCuentaCreditoIsEmpty() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.50");
            String cuentaDebito = "1234567890123456789012345678901234";
            String cuentaCredito = "";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuentaDebito, cuentaCredito, fechaTransferencia)
            );
            assertEquals("La cuenta crédito no puede estar vacía", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la cuenta de crédito exceda la longitud máxima")
        void shouldThrowExceptionWhenCuentaCreditoExceedsMaxLength() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.50");
            String cuentaDebito = "1234567890123456789012345678901234";
            String cuentaCredito = "A".repeat(35); // 35 characters
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuentaDebito, cuentaCredito, fechaTransferencia)
            );
            assertEquals("La cuenta crédito no puede exceder 34 caracteres", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando las cuentas sean las mismas")
        void shouldThrowExceptionWhenCuentasAreTheSame() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.50");
            String cuenta = "1234567890123456789012345678901234";
            LocalDate fechaTransferencia = LocalDate.now();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuenta, cuenta, fechaTransferencia)
            );
            assertEquals("Las cuentas débito y crédito deben ser diferentes", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la fecha transferencia sea nula")
        void shouldThrowExceptionWhenFechaTransferenciaIsNull() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.50");
            String cuentaDebito = "1234567890123456789012345678901234";
            String cuentaCredito = "9876543210987654321098765432109876";

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuentaDebito, cuentaCredito, null)
            );
            assertEquals("La fecha de transferencia no puede ser nula", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando la fecha de transferencia sea futura")
        void shouldThrowExceptionWhenFechaTransferenciaIsInTheFuture() {
            // Given
            Empresa empresa = createValidEmpresa();
            BigDecimal importe = new BigDecimal("1000.50");
            String cuentaDebito = "1234567890123456789012345678901234";
            String cuentaCredito = "9876543210987654321098765432109876";
            LocalDate fechaFutura = LocalDate.now().plusDays(1);

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transferencia(1L, empresa, importe, cuentaDebito, cuentaCredito, fechaFutura)
            );
            assertEquals("La fecha de transferencia no puede ser futura", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Setter Validation Tests")
    class SetterValidationTests {

        @Test
        @DisplayName("Se debe validar la importación al configurarla a través del configurador")
        void shouldValidateImporteWhenSettingViaSetter() {
            // Given
            Transferencia transferencia = Transferencia.builder()
                    .id(1L)
                    .empresa(createValidEmpresa())
                    .importe(new BigDecimal("1000.50"))
                    .cuentaDebito("1234567890123456789012345678901234")
                    .cuentaCredito("9876543210987654321098765432109876")
                    .fechaTransferencia(LocalDate.now())
                    .build();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transferencia.setImporte(BigDecimal.ZERO)
            );
            assertEquals("El importe debe ser mayor a cero", exception.getMessage());
        }

        @Test
        @DisplayName("Debe validar la cuenta débito al configurarla a través del configurador")
        void shouldValidateCuentaDebitoWhenSettingViaSetter() {
            // Given
            Transferencia transferencia = Transferencia.builder()
                    .id(1L)
                    .empresa(createValidEmpresa())
                    .importe(new BigDecimal("1000.50"))
                    .cuentaDebito("1234567890123456789012345678901234")
                    .cuentaCredito("9876543210987654321098765432109876")
                    .fechaTransferencia(LocalDate.now())
                    .build();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transferencia.setCuentaDebito("")
            );
            assertEquals("La cuenta débito no puede estar vacía", exception.getMessage());
        }
    }
}
