use pet_society;
INSERT INTO Administrator (Nombre, Cedula, Telefono, Email) VALUES
("Adrian Ortiz", "1005327319", "3173109599", "dxniel7328@gmail.com"),
("Juan Carlos Marino", "1", "316161616", "juan");

-- Inserción de Propietarios
INSERT INTO Propietarios (Nombre, Cedula, Direccion, Telefono, Email, ContactoEmergencia) VALUES 
('Carlos Gómez', '1234567890', 'Calle Ficticia 100', '555-1234', 'carlosgomez@mail.com', 'Ana Gómez'),
('Laura Fernández', '0987654321', 'Avenida Principal 200', '555-5678', 'laurafernandez@mail.com', 'Luis Fernández'),
('Pedro Sánchez', '1122334455', 'Calle Real 300', '555-8765', 'pedrosanchez@mail.com', 'Marta Sánchez');

-- Inserción de Mascotas
INSERT INTO Mascotas (Nombre, Raza, FechaNacimiento, Sexo, Microchip, Estatus, ID_Propietario, Especie) VALUES 
('Rex', 'Pastor Alemán', '2021-05-15', 'MALE', 'M12345', 'ACTIVA', 1, 'Canino'),
('Luna', 'Siamés', '2020-08-20', 'FEMALE', 'S67890', 'ADOPTADA', 2, 'Felino'),
('Max', 'Golden Retriever', '2019-03-10', 'MALE', 'M23456', 'ACTIVA', 1, 'Canino'),
('Bella', 'Ragdoll', '2022-01-10', 'FEMALE', 'S34567', 'FALLECIDA', 3, 'Felino');

-- Inserción de HistorialMedidas
INSERT INTO HistorialMedidas (ID_Mascota, Peso, Altura) VALUES 
(1, 25.5, 60.2),  -- Rex
(3, 32.0, 65.0);  -- Max

-- Inserción de Veterinarios
INSERT INTO Veterinarios (Cedula, Nombre, Licencia, Especialidad, Telefono, Email) VALUES 
('V123456', 'Dra. Mariana López', 'L001', 'Cirugía General', '555-2345', 'marianalopez@mail.com'),
('V654321', 'Dr. Gabriel Pérez', 'L002', 'Dermatología Veterinaria', '555-6789', 'gabrielperez@mail.com'),
('V654381', 'Dr. Gabriela Pérez', 'L7002', 'Dermatología Veterinaria', '317-3109', 'gabrielaperez@mail.com'),
('V789012', 'Dra. Silvia Ruiz', 'L003', 'Medicina Preventiva', '555-1122', 'silviaruiz@mail.com');

INSERT INTO Veterinarios (Cedula, Nombre, Licencia, Especialidad, Telefono, Email) VALUES 
('V654361', 'Dr. Gabriela Pérez', 'L7082', 'Dermatología Veterinaria', '317-584', 'litokirino@mail.com');

INSERT INTO Categorias_Insumos (Nombre) VALUES 
('Medicamentos'),
('Vacunas'),
('Material Medico'),
("Desparacitario");

-- Inserción de Proveedores
INSERT INTO Proveedores (Nombre, Telefono, Email, Direccion, Tipo_Proveedor) VALUES 
('Proveedor A', '123456789', 'proveedoraA@mail.com', 'Calle Ficticia 123', 'MATERIAL_MEDICO'),
('Proveedor B', '987654321', 'proveedorB@mail.com', 'Avenida Real 456', 'VACUNAS'),
('Proveedor C', '111223344', 'proveedorC@mail.com', 'Calle Luna 789', 'MEDICAMENTOS');

-- Inserción de Insumos Generales
INSERT INTO Insumos (Nombre, Fabricante, UnidadMedida, CategoriaID, NivelMinimo, Stock, PrecioUnitario, ID_Proveedor) VALUES 
('Paracetamol', 'Farmaceutica A', 'Caja', 1, 10, 100, 50.00, 3),  -- Medicamento, categoría 1, proveedor C
('Amoxicilina', 'Farmaceutica B', 'Caja', 1, 5, 200, 30.00, 3),  -- Medicamento, categoría 1, proveedor C
('Vacuna Rabia', 'Laboratorio A', 'Frasco', 2, 5, 50, 100.00, 2),  -- Vacuna, categoría 2, proveedor B
('Vacuna Parvovirus', 'Laboratorio B', 'Frasco', 2, 10, 80, 120.00, 2),  -- Vacuna, categoría 2, proveedor B
('Jeringas', 'Materiales Médicos SA', 'Caja', 3, 15, 500, 20.00, 1),  -- Material Médico, categoría 3, proveedor A
('Guantes Estériles', 'Materiales Médicos SA', 'Caja', 3, 20, 300, 10.00, 1),  -- Material Médico, categoría 3, proveedor A
("Collar desparacitario", "Laboratorio A", "Unidad", 4, 10, 20, 10.00, 1),
("Pastillas comestibles", "Laboratorio A", "Caja", 4, 10, 20, 20.00, 1);

INSERT INTO Medicamentos (ID, Tipo, TipoEspecie, FechaVencimiento) VALUES 
(1, 'Antiinflamatorio', 'Canino', '2025-12-31'),  -- Paracetamol
(2, 'Antibiótico', 'Felino', '2026-06-30');  -- Amoxicilina

INSERT INTO Vacunas (ID, TipoEspecie, Lote, FechaIngreso, FechaVencimiento) VALUES 
(3, 'Canino', 'L12345', '2024-01-01', '2026-01-01'),  -- Vacuna Rabia
(4, 'Felino', 'L67890', '2024-02-01', '2026-02-01');  -- Vacuna Parvovirus

INSERT INTO Material_Medico (ID, Tipo) VALUES 
(5, 'Jeringa de 10ml'),  -- Jeringas
(6, 'Guantes quirúrgicos');  -- Guantes Estériles

INSERT INTO Desparacitarios (ID, TipoAntiparasitario, TipoEspecie, FechaVencimiento) VALUES
(7, "Externo", "Canino", null),
(8, "Interno", "Felino", '2026-02-01');

INSERT INTO Servicios (
    Nombre, 
    TipoServicio, 
    CostoBase
)
VALUES (
    'Consulta General', 
    'Consulta_Basica', 
    50.00
);

INSERT INTO Servicios (
    Nombre, 
    TipoServicio, 
    CostoBase
)
VALUES (
    'Cirugía de Pata', 
    'Cirugia', 
    150.00
);

INSERT INTO Servicios (
    Nombre, 
    TipoServicio, 
    CostoBase
)
VALUES (
    'Desparacitación', 
    'Desparacitacion', 
    30.00
);

INSERT INTO Servicios (Nombre, TipoServicio, CostoBase) VALUES (
	"Arrancar Dientes",
    "Procedimiento_Especial",
    15.00
);

INSERT INTO TiposServicioAdicional(Nombre, CostoBase)VALUES
("Baño y peluqueria", 20.0),
("Guarderia", 50.0),
("Adiestramiento", 60.0);

INSERT INTO ClubMascotasFrecuentes (ID_Propietario, PuntosAcumulados) VALUES
(1,250);