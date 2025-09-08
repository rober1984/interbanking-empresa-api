CREATE TABLE empresa
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    cuit           VARCHAR(20)  NOT NULL UNIQUE,
    razon_social   VARCHAR(255) NOT NULL,
    fecha_adhesion DATE NULL
);

CREATE TABLE transferencia
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    importe             DECIMAL(15, 2) NOT NULL,
    id_empresa          BIGINT         NOT NULL,
    cuenta_debito       VARCHAR(34)    NOT NULL,
    cuenta_credito      VARCHAR(34)    NOT NULL,
    fecha_transferencia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_empresa FOREIGN KEY (id_empresa) REFERENCES empresa (id)
);
