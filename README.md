# 🏦 Interbanking Empresa API by Roberto Rodriguez

API REST para la gestión de empresas y adhesiones al sistema interbancario, desarrollada con **Arquitectura Hexagonal** y **Domain-Driven Design (DDD)**.

## 📋 Tabla de Contenidos

- [🚀 Cómo Levantar el Proyecto](#-cómo-levantar-el-proyecto)
- [🏗️ Arquitectura y Tecnologías](#️-arquitectura-y-tecnologías)
- [📚 Documentación de Endpoints](#-documentación-de-endpoints)
- [🗃️ Esquema y Datos Iniciales](#-esquema-y-datos-iniciales)
- [Base de Datos H2](#-base-de-datos-h2)
- [�� Testing](#-testing)
- [🐳 Docker](#-docker)

---

## 🚀 Cómo Levantar el Proyecto

### Prerrequisitos

- **Java 21** o superior
- **Maven 3.9+**
- **Docker** (opcional)

### Opción 1: Ejecución Local

```bash
# 1. Clonar el repositorio
git clone <repository-url>
cd interbanking-empresa-api

# 2. Compilar y ejecutar
mvn clean install
mvn spring-boot:run

# 3. La aplicación estará disponible en:
# http://localhost:8080
```

### Opción 2: Con Docker

```bash
# 1. Construir y ejecutar con Docker Compose
docker-compose up -d

# 2. Ver logs
docker-compose logs -f

# 3. Detener
docker-compose down
```

### 🔍 Verificación

Una vez levantada la aplicación, puedes verificar que funciona correctamente:

- **API Base**: http://localhost:8080/api/v1/empresas
- **H2 Console**: http://localhost:8080/h2-console
- **Swagger UI**: http://localhost:8080/swagger-ui.html (si se agrega)

---

## 🏗️ Arquitectura y Tecnologías

### 🎯 Arquitectura Hexagonal (Ports & Adapters)

El proyecto implementa **Arquitectura Hexagonal** siguiendo los principios de **Domain-Driven Design (DDD)**:

```
┌─────────────────────────────────────────────────────────────┐
│ 🌐 INFRASTRUCTURE │
│ ┌─────────────────┐ ┌─────────────────┐ ┌─────────────┐ │
│ │ Controllers │ │ Repositories │ │ Database │ │
│ │ (Adapters) │ │ (Adapters) │ │ (H2) │ │
│ └─────────────────┘ └─────────────────┘ └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
↕
┌─────────────────────────────────────────────────────────────┐
│ 🎯 APPLICATION │
│ ┌─────────────────┐ ┌─────────────────┐ ┌─────────────┐ │
│ │ Use Cases │ │ DTOs │ │ Mappers │ │
│ │ (Services) │ │ (Data) │ │ (Convert) │ │
│ └─────────────────┘ └─────────────────┘ └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
↕
┌─────────────────────────────────────────────────────────────┐
│ 💎 DOMAIN │
│ ┌─────────────────┐ ┌─────────────────┐ ┌─────────────┐ │
│ │ Models │ │ Ports │ │ Value │ │
│ │ (Entities) │ │ (Interfaces) │ │ Objects │ │
│ └─────────────────┘ └─────────────────┘ └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

### 🛠️ Stack Tecnológico

| Categoría | Tecnología | Versión | Propósito |
|-----------|---------|---------|-----------|
| **Runtime** | Java | 21 | Lenguaje de programación |
| **Framework** | Spring Boot | 3.2.0 | Framework principal |
| **Web** | Spring Web | 3.2.0 | API REST |
| **Persistence** | Spring Data JPA | 3.2.0 | ORM y acceso a datos |
| **Database** | H2 Database | - | Base de datos en memoria |
| **Build** | Maven | 3.9+ | Gestión de dependencias |
| **Testing** | JUnit 5 | 5.x | Testing framework |
| **Testing** | Mockito | 5.x | Mocking framework |
| **Utilities** | Lombok | 1.18.34 | Reducción de boilerplate |
| **Validation** | Jakarta Validation | - | Validaciones |
| **Logging** | SLF4J | - | Logging estructurado |

### 🏛️ Estructura del Proyecto
```
src/main/java/com/interbanking/empresa/api/
├── 🎯 domain/                               # Capa de Dominio (Core de la aplicación)
│   ├── model/                              # Entidades de negocio (modelos con lógica)
│   │   ├── Empresa.java
│   │   └── Transferencia.java
│   ├── port/                               # Puertos de salida (interfaces del repositorio)
│   │   ├── EmpresaRepositoryPort.java
│   │   └── TransferenciaRepositoryPort.java
│   └── vo/                                 # Value Objects (objetos de valor inmutable)
│       └── RangoMes.java
│
├── 🎯 application/                          # Capa de Aplicación (Casos de Uso)
│   └── usecase/                            # Orquestación de la lógica de negocio
│       ├── AdhesionEmpresaUseCase.java
│       ├── ObtenerEmpresasAdheridasUltimoMesUseCase.java
│       └── ObtenerEmpresasConTransferenciasUltimoMesUseCase.java
│
├── 🌐 controller/                           # Adaptador de Entrada (API REST)
│   ├── EmpresaController.java              # Lógica de la API y manejo de peticiones
│   ├── dto/                                # DTOs para la comunicación con la API
│   └── mapper/                             # Mappers para convertir DTOs a modelos de dominio
│
└── 🌐 infrastructure/                       # Adaptadores de Salida (Persistencia)
└── persistence/                        # Implementación de la persistencia
├── adapter/                        # Adaptadores (implementaciones de los puertos)
├── entity/                         # Entidades JPA (mapeo a la base de datos)
├── mapper/                         # Mappers para convertir entidades JPA a modelos de dominio
└── repository/                     # Repositorios JPA (Spring Data)
```

### 🎯 Principios Aplicados

- **✅ Separación de Responsabilidades**: Cada capa tiene una responsabilidad específica
- **✅ Inversión de Dependencias**: El dominio no depende de infraestructura
- **✅ Domain-Driven Design**: Modelos ricos con lógica de negocio
- **✅ Clean Architecture**: Flujo de dependencias hacia adentro
- **✅ SOLID Principles**: Código mantenible y extensible

---

## 📚 Documentación de Endpoints

### 🧾 Swagger / OpenAPI

La documentación interactiva de la API está disponible automáticamente gracias a `springdoc-openapi`.

- **Swagger UI**: http://localhost:8080/swagger-ui.html (alternativa: http://localhost:8080/swagger-ui/index.html)
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **OpenAPI YAML**: http://localhost:8080/v3/api-docs.yaml

Puedes explorar y probar los endpoints directamente desde Swagger UI.

### Base URL
http://localhost:8080/api/v1/empresas


### 📋 Endpoints Disponibles

#### 1. 🏢 Adherir Empresa

**PATCH** `/api/v1/empresas/{cuit}/adhesion`

Marca una empresa como adherida al sistema interbancario usando su CUIT.

**Parámetros:**
- `cuit` (path): CUIT de la empresa (formato: XXXXXXXXXXX)

**Respuestas:**

| Código | Descripción | Ejemplo |
|--------|-------------|---------|
| `200 OK` | Adhesión exitosa | `"Empresa adherida correctamente"` |
| `409 Conflict` | Empresa ya adherida | `"La empresa ya está adherida desde 2024-01-15"` |
| `404 Not Found` | Empresa no encontrada | `"La empresa con CUIT 20-12345678-9 no existe"` |

**Ejemplo de Uso:**
```bash
curl -X PATCH "http://localhost:8080/api/v1/empresas/20-12345678-9/adhesion"
```

#### 2. 📊 Empresas Adheridas Último Mes

**GET** `/api/v1/empresas/adhesiones/ultimo-mes`

Obtiene todas las empresas que se adhirieron en el último mes.

**Respuesta:**
```json
[
  {
    "id": 1,
    "cuit": "20123456789",
    "razonSocial": "Empresa Ejemplo S.A.",
    "fechaAdhesion": "2024-01-15"
  },
  {
    "id": 2,
    "cuit": "27876543210",
    "razonSocial": "Otra Empresa S.R.L.",
    "fechaAdhesion": "2024-01-20"
  }
]
```

**Ejemplo de Uso:**
```bash
curl -X GET "http://localhost:8080/api/v1/empresas/adhesiones/ultimo-mes"
```

#### 3. 💰 Empresas con Transferencias Último Mes

**GET** `/api/v1/empresas/transferencias/ultimo-mes`

Obtiene todas las empresas que realizaron transferencias en el último mes.

**Respuesta:**
```json
[
  {
    "id": 1,
    "cuit": "20123456789",
    "razonSocial": "Empresa Ejemplo S.A.",
    "fechaAdhesion": "2024-01-15"
  }
]
```

**Ejemplo de Uso:**
```bash
curl -X GET "http://localhost:8080/api/v1/empresas/transferencias/ultimo-mes"
```

### �� Base de Datos H2

**URL:** http://localhost:8080/h2-console

**Configuración:**
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** (vacío)

### 🗃️ Esquema y Datos Iniciales

Tablas creadas (ver `src/main/resources/schema.sql`):

- **empresa**
  - `id` BIGINT PK AUTO_INCREMENT
  - `cuit` VARCHAR(20) UNIQUE NOT NULL
  - `razon_social` VARCHAR(255) NOT NULL
  - `fecha_adhesion` DATE NULL

- **transferencia**
  - `id` BIGINT PK AUTO_INCREMENT
  - `importe` DECIMAL(15,2) NOT NULL
  - `id_empresa` BIGINT NOT NULL (FK → `empresa.id`)
  - `cuenta_debito` VARCHAR(34) NOT NULL
  - `cuenta_credito` VARCHAR(34) NOT NULL
  - `fecha_transferencia` TIMESTAMP DEFAULT CURRENT_TIMESTAMP

Datos de ejemplo cargados (ver `src/main/resources/data.sql`):

- `empresa`: 3 registros
  - `('20123456789', 'Empresa Uno S.A.', '2025-08-01')`
  - `('20987654321', 'Empresa Dos SRL', NULL)`
  - `('20345678901', 'Empresa Tres S.A.', '2025-07-15')`

- `transferencia`: 3 registros
  - `(15000.50, empresa_id=1, 'AR123000100123456789', 'AR450002001234567890', '2025-08-15 10:30:00')`
  - `(2500.00, empresa_id=1, 'AR123000100123456789', 'AR890002009876543210', '2025-08-20 15:45:00')`
  - `(87000.75, empresa_id=3, 'AR450002001234567890', 'AR123000100123456789', '2025-08-01 09:00:00')`

---

## 🧪 Testing

### Ejecutar Tests

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con reporte
mvn test -Dmaven.test.failure.ignore=true

# Ejecutar tests específicos
mvn test -Dtest=EmpresaTest
```

### Cobertura de Tests

El proyecto incluye tests unitarios para:

- **✅ Domain Models**: Validaciones de negocio
- **✅ Use Cases**: Lógica de aplicación
- **✅ Controllers**: Endpoints REST
- **✅ Integration**: Flujos completos

**Estructura de Tests:**
```
src/test/java/
├── domain/model/
│ ├── EmpresaTest.java # 13 tests
│ └── TransferenciaTest.java # 17 tests
├── application/usecase/
│ └── AdhesionEmpresaUseCaseTest.java # 4 tests
└── controller/
└── EmpresaControllerTest.java # 7 tests
```
---

## �� Docker

### Docker Compose

```bash
# Levantar servicios
docker-compose up -d

# Ver logs
docker-compose logs -f interbanking-api

# Detener servicios
docker-compose down
```

### Variables de Entorno

| Variable | Valor por Defecto | Descripción |
|----------|-------------------|-------------|
| `SPRING_DATASOURCE_URL` | `jdbc:h2:mem:testdb` | URL de conexión a BD |
| `SPRING_DATASOURCE_USERNAME` | `sa` | Usuario de BD |
| `SPRING_DATASOURCE_PASSWORD` | (vacío) | Contraseña de BD |
| `SERVER_PORT` | `8080` | Puerto de la aplicación |

---

## 🚀 Características Destacadas

- **🏗️ Arquitectura Hexagonal**: Separación clara de responsabilidades
- **💎 Domain-Driven Design**: Modelos ricos con validaciones de negocio
- **🧪 Testing**: Cobertura de algunas de las capas
- **🐳 Dockerizado**: Fácil despliegue y ejecución
- **📝 Logging Estructurado**: Trazabilidad de caso de uso de adhesion de empresa
- **🔒 Validaciones Robustas**: CUIT, importes, fechas
- **📊 Consultas Optimizadas**: Queries eficientes con JPA
- **🎯 RESTful API**: Diseño de endpoints siguiendo estándares
