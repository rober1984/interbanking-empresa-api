-- Empresas
INSERT INTO empresa (cuit, razon_social, fecha_adhesion)
VALUES ('20123456789', 'Empresa Uno S.A.', '2025-08-01');

INSERT INTO empresa (cuit, razon_social, fecha_adhesion)
VALUES ('20987654321', 'Empresa Dos SRL', NULL);

INSERT INTO empresa (cuit, razon_social, fecha_adhesion)
VALUES ('20345678901', 'Empresa Tres S.A.', '2025-07-15');

-- Transferencias
INSERT INTO transferencia (importe, id_empresa, cuenta_debito, cuenta_credito, fecha_transferencia)
VALUES (15000.50, 1, 'AR123000100123456789', 'AR450002001234567890', '2025-08-15 10:30:00');

INSERT INTO transferencia (importe, id_empresa, cuenta_debito, cuenta_credito, fecha_transferencia)
VALUES (2500.00, 1, 'AR123000100123456789', 'AR890002009876543210', '2025-08-20 15:45:00');

INSERT INTO transferencia (importe, id_empresa, cuenta_debito, cuenta_credito, fecha_transferencia)
VALUES (87000.75, 3, 'AR450002001234567890', 'AR123000100123456789', '2025-08-01 09:00:00');
