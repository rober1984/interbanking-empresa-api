package com.interbanking.empresa.api.controller;

import com.interbanking.empresa.api.application.usecase.AdhesionEmpresaUseCase;
import com.interbanking.empresa.api.application.usecase.ObtenerEmpresasAdheridasUltimoMesUseCase;
import com.interbanking.empresa.api.application.usecase.ObtenerEmpresasConTransferenciasUltimoMesUseCase;
import com.interbanking.empresa.api.application.usecase.dto.response.AdhesionEmpresaResponse;
import com.interbanking.empresa.api.domain.model.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmpresaController Tests")
class EmpresaControllerTest {

    @Mock
    private AdhesionEmpresaUseCase adhesionEmpresaUseCase;

    @Mock
    private ObtenerEmpresasAdheridasUltimoMesUseCase obtenerEmpresasAdheridasUltimoMesUseCase;

    @Mock
    private ObtenerEmpresasConTransferenciasUltimoMesUseCase obtenerEmpresasConTransferenciasUltimoMesUseCase;

    private EmpresaController empresaController;

    @BeforeEach
    void setUp() {
        empresaController = new EmpresaController(
                adhesionEmpresaUseCase,
                obtenerEmpresasAdheridasUltimoMesUseCase,
                obtenerEmpresasConTransferenciasUltimoMesUseCase
        );
    }

    @Nested
    @DisplayName("Adhesion Endpoint Tests")
    class AdhesionEndpointTests {

        @Test
        @DisplayName("Debe devolver una respuesta de éxito cuando la adhesión sea exitosa")
        void shouldReturnSuccessResponseWhenAdhesionIsSuccessful() {
            // Given
            String cuit = "20123456789";
            AdhesionEmpresaResponse successResponse = AdhesionEmpresaResponse.success(
                    new com.interbanking.empresa.api.application.usecase.dto.response.AdhesionEmpresaDto(
                            1L, cuit, "Empresa Test S.A.", LocalDate.now()
                    )
            );

            when(adhesionEmpresaUseCase.procesarAdhesion(cuit)).thenReturn(successResponse);

            // When
            ResponseEntity<String> response = empresaController.adhesionEmpresa(cuit);

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Empresa adherida correctamente", response.getBody());

            verify(adhesionEmpresaUseCase).procesarAdhesion(cuit);
        }

        @Test
        @DisplayName("Debe retornar una respuesta de no funciona cuando la empresa no exista")
        void shouldReturnNotFoundResponseWhenEmpresaDoesNotExist() {
            // Given
            String cuit = "20123456789";
            AdhesionEmpresaResponse notFoundResponse = AdhesionEmpresaResponse.notFound(
                    "La empresa con CUIT " + cuit + " no existe"
            );

            when(adhesionEmpresaUseCase.procesarAdhesion(cuit)).thenReturn(notFoundResponse);

            // When
            ResponseEntity<String> response = empresaController.adhesionEmpresa(cuit);

            // Then
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("La empresa con CUIT " + cuit + " no existe", response.getBody());

            verify(adhesionEmpresaUseCase).procesarAdhesion(cuit);
        }

        @Test
        @DisplayName("Debe retornar una respuesta de conflicto cuando la empresa ya este adherida")
        void shouldReturnConflictResponseWhenEmpresaIsAlreadyAdhered() {
            // Given
            String cuit = "20123456789";
            LocalDate fechaAdhesion = LocalDate.now().minusDays(30);
            AdhesionEmpresaResponse conflictResponse = AdhesionEmpresaResponse.alreadyAdhered(
                    "La empresa ya está adherida desde " + fechaAdhesion
            );

            when(adhesionEmpresaUseCase.procesarAdhesion(cuit)).thenReturn(conflictResponse);

            // When
            ResponseEntity<String> response = empresaController.adhesionEmpresa(cuit);

            // Then
            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertEquals("La empresa ya está adherida desde " + fechaAdhesion, response.getBody());

            verify(adhesionEmpresaUseCase).procesarAdhesion(cuit);
        }
    }

    @Nested
    @DisplayName("Obtener Empresas Adheridas del Ultimo Mes")
    class GetEmpresasAdheridasUltimoMesTests {

        @Test
        @DisplayName("Debe retornar una lista de empresas empresas adheridas ultimo mes")
        void shouldReturnListOfEmpresasAdheridasUltimoMes() {
            // Given
            List<Empresa> empresas = Arrays.asList(
                    Empresa.builder()
                            .id(1L)
                            .cuit("20123456789")
                            .razonSocial("Empresa 1 S.A.")
                            .fechaAdhesion(LocalDate.now().minusDays(15))
                            .build(),
                    Empresa.builder()
                            .id(2L)
                            .cuit("20987654321")
                            .razonSocial("Empresa 2 S.A.")
                            .fechaAdhesion(LocalDate.now().minusDays(10))
                            .build()
            );

            when(obtenerEmpresasAdheridasUltimoMesUseCase.ejecutar()).thenReturn(empresas);

            // When
            ResponseEntity<List<com.interbanking.empresa.api.controller.dto.response.EmpresaResponse>> response = 
                    empresaController.getEmpresasAdheridasUltimoMes();

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(2, response.getBody().size());

            com.interbanking.empresa.api.controller.dto.response.EmpresaResponse empresa1 = response.getBody().get(0);
            assertEquals(1L, empresa1.id());
            assertEquals("20123456789", empresa1.cuit());
            assertEquals("Empresa 1 S.A.", empresa1.razonSocial());

            verify(obtenerEmpresasAdheridasUltimoMesUseCase).ejecutar();
        }

        @Test
        @DisplayName("Debe retornar una lista vacia cuando no haya empresas empresas adheridas")
        void shouldReturnEmptyListWhenNoEmpresasAreFound() {
            // Given
            when(obtenerEmpresasAdheridasUltimoMesUseCase.ejecutar()).thenReturn(Arrays.asList());

            // When
            ResponseEntity<List<com.interbanking.empresa.api.controller.dto.response.EmpresaResponse>> response = 
                    empresaController.getEmpresasAdheridasUltimoMes();

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().isEmpty());

            verify(obtenerEmpresasAdheridasUltimoMesUseCase).ejecutar();
        }
    }

    @Nested
    @DisplayName("Obtener Empresas Con Transferencias del Ultimo Mes")
    class GetEmpresasConTransferenciasUltimoMesTests {

        @Test
        @DisplayName("Debe retornar una lista de empresas con transferencias del ultimo mes")
        void shouldReturnListOfEmpresasConTransferenciasUltimoMes() {
            // Given
            List<Empresa> empresas = Arrays.asList(
                    Empresa.builder()
                            .id(1L)
                            .cuit("20123456789")
                            .razonSocial("Empresa 1 S.A.")
                            .fechaAdhesion(LocalDate.now().minusDays(30))
                            .build(),
                    Empresa.builder()
                            .id(2L)
                            .cuit("20987654321")
                            .razonSocial("Empresa 2 S.A.")
                            .fechaAdhesion(LocalDate.now().minusDays(25))
                            .build()
            );

            when(obtenerEmpresasConTransferenciasUltimoMesUseCase.ejecutar()).thenReturn(empresas);

            // When
            ResponseEntity<List<com.interbanking.empresa.api.controller.dto.response.EmpresaResponse>> response = 
                    empresaController.getEmpresasConTransferenciasUltimoMes();

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(2, response.getBody().size());

            com.interbanking.empresa.api.controller.dto.response.EmpresaResponse empresa1 = response.getBody().get(0);
            assertEquals(1L, empresa1.id());
            assertEquals("20123456789", empresa1.cuit());
            assertEquals("Empresa 1 S.A.", empresa1.razonSocial());

            verify(obtenerEmpresasConTransferenciasUltimoMesUseCase).ejecutar();
        }

        @Test
        @DisplayName("Debe devolver una lista vacía cuando no se encuentran empresas con transferencias")
        void shouldReturnEmptyListWhenNoEmpresasWithTransferenciasAreFound() {
            // Given
            when(obtenerEmpresasConTransferenciasUltimoMesUseCase.ejecutar()).thenReturn(Arrays.asList());

            // When
            ResponseEntity<List<com.interbanking.empresa.api.controller.dto.response.EmpresaResponse>> response = 
                    empresaController.getEmpresasConTransferenciasUltimoMes();

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().isEmpty());

            verify(obtenerEmpresasConTransferenciasUltimoMesUseCase).ejecutar();
        }
    }
}
