package com.interbanking.empresa.api.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Empresa Domain Model Tests")
class EmpresaTest {

    @Nested
    @DisplayName("Constructor Validation Tests")
    class ConstructorValidationTests {

        @Test
        @DisplayName("Debe crear una empresa con datos válidos")
        void shouldCreateEmpresaWithValidData() {
            // Given
            Long id = 1L;
            String cuit = "20123456789";
            String razonSocial = "Empresa Test S.A.";
            LocalDate fechaAdhesion = LocalDate.now();

            // When
            Empresa empresa = new Empresa(id, cuit, razonSocial, fechaAdhesion);

            // Then
            assertNotNull(empresa);
            assertEquals(id, empresa.getId());
            assertEquals(cuit, empresa.getCuit());
            assertEquals(razonSocial, empresa.getRazonSocial());
            assertEquals(fechaAdhesion, empresa.getFechaAdhesion());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando CUIT sea nulo")
        void shouldThrowExceptionWhenCuitIsNull() {
            // Given
            String cuit = null;
            String razonSocial = "Empresa Test S.A.";

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Empresa(1L, cuit, razonSocial, null)
            );
            assertEquals("El CUIT no puede estar vacío", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando CUIT esté vacío")
        void shouldThrowExceptionWhenCuitIsEmpty() {
            // Given
            String cuit = "";
            String razonSocial = "Empresa Test S.A.";

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Empresa(1L, cuit, razonSocial, null)
            );
            assertEquals("El CUIT no puede estar vacío", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando CUIT tenga un formato no válido")
        void shouldThrowExceptionWhenCuitHasInvalidFormat() {
            // Given
            String cuit = "123456789"; // Only 9 digits
            String razonSocial = "Empresa Test S.A.";

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Empresa(1L, cuit, razonSocial, null)
            );
            assertEquals("El CUIT debe tener exactamente 11 dígitos", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando razon social sea nulo")
        void shouldThrowExceptionWhenRazonSocialIsNull() {
            // Given
            String cuit = "20123456789";
            String razonSocial = null;

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Empresa(1L, cuit, razonSocial, null)
            );
            assertEquals("La razón social no puede estar vacía", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando razon social sea vacío")
        void shouldThrowExceptionWhenRazonSocialIsEmpty() {
            // Given
            String cuit = "20123456789";
            String razonSocial = "";

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Empresa(1L, cuit, razonSocial, null)
            );
            assertEquals("La razón social no puede estar vacía", exception.getMessage());
        }

        @Test
        @DisplayName("Debería lanzar una excepción cuando Razon Social exceda la longitud máxima")
        void shouldThrowExceptionWhenRazonSocialExceedsMaxLength() {
            // Given
            String cuit = "20123456789";
            String razonSocial = "A".repeat(256); // 256 characters

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Empresa(1L, cuit, razonSocial, null)
            );
            assertEquals("La razón social no puede exceder 255 caracteres", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Business Methods Tests")
    class BusinessMethodsTests {

        @Test
        @DisplayName("Debería adherirse la empresa con éxito\n")
        void shouldAdhereEmpresaSuccessfully() {
            // Given
            Empresa empresa = Empresa.builder()
                    .id(1L)
                    .cuit("20123456789")
                    .razonSocial("Empresa Test S.A.")
                    .build();

            // When
            empresa.adherir();

            // Then
            assertTrue(empresa.estaAdherida());
            assertNotNull(empresa.getFechaAdhesion());
            assertEquals(LocalDate.now(), empresa.getFechaAdhesion());
        }

        @Test
        @DisplayName("Debería lanzar una excepción al intentar adherirse a una empresa ya adherida")
        void shouldThrowExceptionWhenTryingToAdhereAlreadyAdheredEmpresa() {
            // Given
            LocalDate fechaAdhesion = LocalDate.now().minusDays(30);
            Empresa empresa = Empresa.builder()
                    .id(1L)
                    .cuit("20123456789")
                    .razonSocial("Empresa Test S.A.")
                    .fechaAdhesion(fechaAdhesion)
                    .build();

            // When & Then
            IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                empresa::adherir
            );
            assertEquals("La empresa ya está adherida desde " + fechaAdhesion, exception.getMessage());
        }

        @Test
        @DisplayName("Debe devolver falso cuando no se cumple con la empresa")
        void shouldReturnFalseWhenEmpresaIsNotAdhered() {
            // Given
            Empresa empresa = Empresa.builder()
                    .id(1L)
                    .cuit("20123456789")
                    .razonSocial("Empresa Test S.A.")
                    .build();

            // When & Then
            assertFalse(empresa.estaAdherida());
        }

        @Test
        @DisplayName("Debe devolver verdadero cuando se cumple con la empresa")
        void shouldReturnTrueWhenEmpresaIsAdhered() {
            // Given
            Empresa empresa = Empresa.builder()
                    .id(1L)
                    .cuit("20123456789")
                    .razonSocial("Empresa Test S.A.")
                    .fechaAdhesion(LocalDate.now())
                    .build();

            // When & Then
            assertTrue(empresa.estaAdherida());
        }
    }

    @Nested
    @DisplayName("Setter Validation Tests")
    class SetterValidationTests {

        @Test
        @DisplayName("Debe validar CUIT al configurarlo a través del configurador\n")
        void shouldValidateCuitWhenSettingViaSetter() {
            // Given
            Empresa empresa = Empresa.builder()
                    .id(1L)
                    .cuit("20123456789")
                    .razonSocial("Empresa Test S.A.")
                    .build();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> empresa.setCuit("123") // Invalid CUIT
            );
            assertEquals("El CUIT debe tener exactamente 11 dígitos", exception.getMessage());
        }

        @Test
        @DisplayName("Se debe validar la razón social al configurar a través del setter")
        void shouldValidateRazonSocialWhenSettingViaSetter() {
            // Given
            Empresa empresa = Empresa.builder()
                    .id(1L)
                    .cuit("20123456789")
                    .razonSocial("Empresa Test S.A.")
                    .build();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> empresa.setRazonSocial("") // Empty razon social
            );
            assertEquals("La razón social no puede estar vacía", exception.getMessage());
        }
    }
}
