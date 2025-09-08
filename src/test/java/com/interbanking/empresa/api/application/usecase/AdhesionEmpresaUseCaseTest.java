package com.interbanking.empresa.api.application.usecase;

import com.interbanking.empresa.api.application.usecase.dto.response.AdhesionEmpresaResponse;
import com.interbanking.empresa.api.domain.model.Empresa;
import com.interbanking.empresa.api.domain.port.EmpresaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdhesionEmpresaUseCase Tests")
class AdhesionEmpresaUseCaseTest {

    @Mock
    private EmpresaRepositoryPort empresaRepositoryPort;

    private AdhesionEmpresaUseCase adhesionEmpresaUseCase;

    @BeforeEach
    void setUp() {
        adhesionEmpresaUseCase = new AdhesionEmpresaUseCase(empresaRepositoryPort);
    }

    @Nested
    @DisplayName("Successful Adhesion Tests")
    class SuccessfulAdhesionTests {

        @Test
        @DisplayName("Debe procesar con éxito la adhesión para empresas no adheridas")
        void shouldProcessAdhesionSuccessfullyForNonAdheredEmpresa() {
            // Given
            String cuit = "20123456789";
            Empresa empresa = Empresa.builder()
                    .id(1L)
                    .cuit(cuit)
                    .razonSocial("Empresa Test S.A.")
                    .build();

            Empresa empresaAdherida = Empresa.builder()
                    .id(1L)
                    .cuit(cuit)
                    .razonSocial("Empresa Test S.A.")
                    .fechaAdhesion(LocalDate.now())
                    .build();

            when(empresaRepositoryPort.findByCuit(cuit)).thenReturn(Optional.of(empresa));
            when(empresaRepositoryPort.save(any(Empresa.class))).thenReturn(empresaAdherida);

            // When
            AdhesionEmpresaResponse response = adhesionEmpresaUseCase.procesarAdhesion(cuit);

            // Then
            assertTrue(response.isSuccess());
            assertEquals("Empresa adherida correctamente", response.getMessage());
            assertNotNull(response.getEmpresa());
            assertEquals(1L, response.getEmpresa().id());
            assertEquals(cuit, response.getEmpresa().cuit());
            assertEquals("Empresa Test S.A.", response.getEmpresa().razonSocial());
            assertNotNull(response.getEmpresa().fechaAdhesion());

            verify(empresaRepositoryPort).findByCuit(cuit);
            verify(empresaRepositoryPort).save(any(Empresa.class));
        }
    }

    @Nested
    @DisplayName("Already Adhered Tests")
    class AlreadyAdheredTests {

        @Test
        @DisplayName("Debería devolver la respuesta ya adherida cuando la empresa ya está adherida")
        void shouldReturnAlreadyAdheredResponseWhenEmpresaIsAlreadyAdhered() {
            // Given
            String cuit = "20123456789";
            LocalDate fechaAdhesion = LocalDate.now().minusDays(30);
            Empresa empresaAdherida = Empresa.builder()
                    .id(1L)
                    .cuit(cuit)
                    .razonSocial("Empresa Test S.A.")
                    .fechaAdhesion(fechaAdhesion)
                    .build();

            when(empresaRepositoryPort.findByCuit(cuit)).thenReturn(Optional.of(empresaAdherida));

            // When
            AdhesionEmpresaResponse response = adhesionEmpresaUseCase.procesarAdhesion(cuit);

            // Then
            assertFalse(response.isSuccess());
            assertEquals("La empresa ya está adherida desde " + fechaAdhesion, response.getMessage());
            assertNull(response.getEmpresa());

            verify(empresaRepositoryPort).findByCuit(cuit);
            verify(empresaRepositoryPort, never()).save(any(Empresa.class));
        }
    }

    @Nested
    @DisplayName("Not Found Tests")
    class NotFoundTests {

        @Test
        @DisplayName("Debería devolver la respuesta 'no encontrada' cuando la empresa no existe")
        void shouldReturnNotFoundResponseWhenEmpresaDoesNotExist() {
            // Given
            String cuit = "20123456789";
            when(empresaRepositoryPort.findByCuit(cuit)).thenReturn(Optional.empty());

            // When
            AdhesionEmpresaResponse response = adhesionEmpresaUseCase.procesarAdhesion(cuit);

            // Then
            assertFalse(response.isSuccess());
            assertEquals("La empresa con CUIT " + cuit + " no existe", response.getMessage());
            assertNull(response.getEmpresa());

            verify(empresaRepositoryPort).findByCuit(cuit);
            verify(empresaRepositoryPort, never()).save(any(Empresa.class));
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Debe manejar correctamente el flujo de adhesión completo")
        void shouldHandleCompleteAdhesionFlowCorrectly() {
            // Given
            String cuit = "20123456789";
            Empresa empresa = Empresa.builder()
                    .id(1L)
                    .cuit(cuit)
                    .razonSocial("Empresa Test S.A.")
                    .build();

            when(empresaRepositoryPort.findByCuit(cuit)).thenReturn(Optional.of(empresa));
            when(empresaRepositoryPort.save(any(Empresa.class))).thenAnswer(invocation -> {
                return invocation.getArgument(0);
            });

            // When
            AdhesionEmpresaResponse response = adhesionEmpresaUseCase.procesarAdhesion(cuit);

            // Then
            assertTrue(response.isSuccess());
            assertNotNull(response.getEmpresa());
            assertTrue(response.getEmpresa().fechaAdhesion().isEqual(LocalDate.now()) ||
                    response.getEmpresa().fechaAdhesion().isBefore(LocalDate.now().plusDays(1)));

            verify(empresaRepositoryPort).findByCuit(cuit);
            verify(empresaRepositoryPort).save(any(Empresa.class));
        }
    }
}
