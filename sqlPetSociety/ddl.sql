create database if not exists pet_society;

use pet_society;

-- Lista
CREATE TABLE Administrator(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Cedula VARCHAR(100) NOT NULL UNIQUE,
    Telefono VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE
);
-- Lista
CREATE TABLE Propietarios (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Cedula VARCHAR(50) NOT NULL UNIQUE,
    Direccion VARCHAR(255),
    Telefono VARCHAR(20),
    Email VARCHAR(100) UNIQUE,
    ContactoEmergencia VARCHAR(100),
    Estatus boolean default 1,
    FechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Lista
CREATE TABLE Mascotas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Raza VARCHAR(100),
    FechaNacimiento DATE,
    Sexo ENUM('MALE', 'FEMALE'),
    Microchip VARCHAR(50),
    Foto VARCHAR(255),
    Estatus ENUM('ACTIVA', 'ADOPTADA', 'FALLECIDA', "ESPERANDO_ADOPCION") DEFAULT 'ACTIVA',
    FechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ID_Propietario INT NULL,
    FOREIGN KEY (ID_Propietario) REFERENCES Propietarios(ID),
    Especie ENUM("Canino","Felino", "Roedor", "Ave")
);
-- Lista
CREATE TABLE HistorialMedidas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Mascota INT NOT NULL,
    Peso DECIMAL(5, 2),
    Altura DECIMAL(10, 2),
    FechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ID_Mascota) REFERENCES Mascotas(ID)
);
-- Lista
CREATE TABLE Veterinarios (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Cedula VARCHAR(100) NOT NULL UNIQUE,
    Nombre VARCHAR(100) NOT NULL,
    Licencia VARCHAR(50) NOT NULL UNIQUE,
    Especialidad VARCHAR(100),
    Telefono VARCHAR(20),
    Email VARCHAR(100),
    Contratado BOOLEAN DEFAULT TRUE
);
-- Lista
CREATE TABLE Proveedores (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Telefono VARCHAR(20),
    Email VARCHAR(100),
    Direccion VARCHAR(255),
    Tipo_Proveedor ENUM("MATERIAL_MEDICO", "VACUNAS", "MEDICAMENTOS")
);
-- Lista
CREATE TABLE Categorias_Insumos (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL
);
-- Lista
CREATE TABLE Insumos (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Fabricante VARCHAR(255) NOT NULL,
    UnidadMedida VARCHAR(50) NOT NULL,
    CategoriaID INT NOT NULL,
    FOREIGN KEY (CategoriaID) REFERENCES Categorias_Insumos(ID),
    NivelMinimo INT NOT NULL,
    Stock INT NOT NULL,
    PrecioUnitario DECIMAL(10,2) NOT NULL,
    ID_Proveedor INT NOT NULL,
    FOREIGN KEY (ID_Proveedor) REFERENCES Proveedores(ID)
);
-- Lista
CREATE TABLE Medicamentos (
    ID INT PRIMARY KEY,
    Tipo VARCHAR(50),
    TipoEspecie ENUM("Canino", "Felino", "Roedor", "Ave"),
    FechaVencimiento DATE NOT NULL,
    FOREIGN KEY (ID) REFERENCES Insumos(ID)
);
-- Lista
CREATE TABLE Vacunas (
    ID INT PRIMARY KEY,
    TipoEspecie ENUM("Canino", "Felino", "Roedor", "Ave"),
    Lote VARCHAR(50),
    FechaIngreso DATE NOT NULL,
    FechaVencimiento DATE NOT NULL,
    FOREIGN KEY (ID) REFERENCES Insumos(ID)
);
-- Lista
CREATE TABLE Desparacitarios (
    ID INT PRIMARY KEY,
    TipoAntiparasitario ENUM("Externo", "Interno"),
    TipoEspecie ENUM("Canino", "Felino", "Roedor", "Ave"),
    FechaVencimiento DATE,
    FOREIGN KEY (ID) REFERENCES Insumos(ID)
);
-- Lista
CREATE TABLE Material_Medico (
    ID INT PRIMARY KEY,
    Tipo VARCHAR(50),
    FOREIGN KEY (ID) REFERENCES Insumos(ID)
);
-- Lista
CREATE TABLE Servicios (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL,
    TipoServicio ENUM("Consulta_Basica", "Procedimiento_Especial", "Cirugia", "Desparacitacion") NOT NULL,
    CostoBase DECIMAL(10,2) NOT NULL
);
-- Lista
CREATE TABLE Servicio_Insumos (
    ServicioID INT NOT NULL,
    InsumoID INT NOT NULL,
    Cantidad INT NOT NULL,
    PRIMARY KEY (ServicioID, InsumoID),
    FOREIGN KEY (ServicioID) REFERENCES Servicios(ID),
    FOREIGN KEY (InsumoID) REFERENCES Insumos(ID)
);
CREATE TABLE HistorialServicios (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    MascotaID INT NOT NULL,
    ServicioID INT NOT NULL,
    VeterinarioID INT NOT NULL,
    FechaAplicacion DATE NOT NULL,
    ProximaDosis DATE,
    FOREIGN KEY (MascotaID) REFERENCES Mascotas(ID),
    FOREIGN KEY (ServicioID) REFERENCES Servicios(ID),
    FOREIGN KEY (VeterinarioID) REFERENCES Veterinarios(ID)
);

CREATE TABLE Desparacitacion (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ServicioID INT NOT NULL,
    DesparacitarioID INT NOT NULL,
    FOREIGN KEY (ServicioID) REFERENCES Servicios(ID),
    FOREIGN KEY (DesparacitarioID) REFERENCES Desparacitarios(ID)
);

CREATE TABLE HistorialVacunacion (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Mascota INT NOT NULL,
    ID_Vacuna INT NOT NULL,
    ID_Veterinario INT NOT NULL,
    FechaAplicacion DATE,
    ProximaDosis DATE,
    FOREIGN KEY (ID_Mascota) REFERENCES Mascotas(ID),
    FOREIGN KEY (ID_Vacuna) REFERENCES Vacunas(ID),
    FOREIGN KEY (ID_Veterinario) REFERENCES Veterinarios(ID)
);

CREATE TABLE HistorialDesparacitacion (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    MascotaID INT NOT NULL,
    DesparacitacionID INT NOT NULL,
    VeterinarioID INT NOT NULL,
    FechaAplicacion DATE NOT NULL,
    ProximaDosis DATE,
    FOREIGN KEY (MascotaID) REFERENCES Mascotas(ID),
    FOREIGN KEY (DesparacitacionID) REFERENCES Desparacitacion(ID),
    FOREIGN KEY (VeterinarioID) REFERENCES Veterinarios(ID)
);

CREATE TABLE Consultas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ServicioID INT NOT NULL,
    MotivoConsulta VARCHAR(255) NOT NULL,
    Diagnostico VARCHAR(255),
    TratamientoRecomendado VARCHAR(255),
    FOREIGN KEY (ServicioID) REFERENCES Servicios(ID)
);
CREATE TABLE ProcedimientosEspeciales (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ServicioID INT NOT NULL,
    DescripcionProcedimiento VARCHAR(255) NOT NULL,
    Duracion TIME NOT NULL,
    DiasRecuperacion INT NOT NULL,
    FOREIGN KEY (ServicioID) REFERENCES Servicios(ID)
);

CREATE TABLE Cirugias (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ServicioID INT NOT NULL,
    DescripcionCirugia VARCHAR(255) NOT NULL,
    Duracion TIME NOT NULL,
    DiasRecuperacion INT NOT NULL,
    FOREIGN KEY (ServicioID) REFERENCES Servicios(ID)
);

CREATE TABLE DetallesConsultas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ConsultaID INT NOT NULL, 
    InsumoID INT,
    ProcedimientoID INT,
    CirugiaID INT,
    Cantidad INT,
    FechaPrescripcion DATE NOT NULL, 
    FOREIGN KEY (ConsultaID) REFERENCES Consultas(ID),
    FOREIGN KEY (InsumoID) REFERENCES Insumos(ID) ON DELETE SET NULL, 
    FOREIGN KEY (ProcedimientoID) REFERENCES ProcedimientosEspeciales(ID) ON DELETE SET NULL,
    FOREIGN KEY (CirugiaID) REFERENCES Cirugias(ID) ON DELETE SET NULL
);

CREATE TABLE OrdenesCompra (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Proveedor INT NOT NULL,
    Fecha DATE,
    Total DECIMAL(10, 2),
    FOREIGN KEY (ID_Proveedor) REFERENCES Proveedores(ID)
);

CREATE TABLE DetalleOrdenCompra (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_OrdenCompra INT NOT NULL,
    ID_Insumo INT NOT NULL,
    Cantidad INT,
    PrecioUnitario DECIMAL(10, 2),
    FOREIGN KEY (ID_OrdenCompra) REFERENCES OrdenesCompra(ID),
    FOREIGN KEY (ID_Insumo) REFERENCES Insumos(ID)
);

CREATE TABLE InsumosProcedimiento (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Procedimiento INT NOT NULL,
    ID_Insumo INT NOT NULL,
    CantidadUsada DECIMAL(10, 2),
    FechaUso DATE,
    FOREIGN KEY (ID_Procedimiento) REFERENCES ProcedimientosEspeciales(ID),
    FOREIGN KEY (ID_Insumo) REFERENCES Insumos(ID)
);
CREATE TABLE TiposServicioAdicional (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    CostoBase DECIMAL(10,2) NOT NULL
);

CREATE TABLE Facturas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NombreEmpresa VARCHAR(100) NOT NULL,
    NIT VARCHAR(50) NOT NULL,
    Fecha DATE NOT NULL,
    Direccion VARCHAR(255),
    Telefono VARCHAR(200),
    Email VARCHAR(100),
    Total DECIMAL(10, 2),
    Cufe VARCHAR(50), 
    ID_Propietario INT NOT NULL,
    FOREIGN KEY (ID_Propietario) REFERENCES Propietarios(ID)
);
CREATE TABLE Citas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Mascota INT NOT NULL,
    Fecha DATE,
    Hora TIME,
    Estado ENUM("Programada", "En_Proceso", "Finalizada"),
    FOREIGN KEY (ID_Mascota) REFERENCES Mascotas(ID)
    
);
CREATE TABLE ServiciosAdicionales (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Cita INT NOT NULL,
    TipoServicioID INT NOT NULL,
    Detalle TEXT,
    Observaciones TEXT,
    Precio decimal(10,2) not null,
    FOREIGN KEY (ID_Cita) REFERENCES Citas(ID),
    FOREIGN KEY (TipoServicioID) REFERENCES TiposServicioAdicional(ID)
);
CREATE TABLE CitasProcedimientos (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Cita INT NOT NULL,
    ID_Procedimiento INT NOT NULL,
    Cantidad DECIMAL(10, 2) DEFAULT 1,
    FOREIGN KEY (ID_Cita) REFERENCES Citas(ID),
    FOREIGN KEY (ID_Procedimiento) REFERENCES ProcedimientosEspeciales(ID)
);

CREATE TABLE DetallesFactura (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Factura INT NOT NULL,
    ID_Servicio INT NOT NULL,
    ID_ServicioAdicional INT NULL,
    Descripcion VARCHAR(255),
    Cantidad INT,
    PrecioUnitario DECIMAL(10, 2),
    FOREIGN KEY (ID_Factura) REFERENCES Facturas(ID),
    FOREIGN KEY (ID_Servicio) REFERENCES Servicios(ID),
    FOREIGN KEY (ID_ServicioAdicional)REFERENCES ServiciosAdicionales(ID)
);

CREATE TABLE DetallesFacturaProductos(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Factura INT NOT NULL,
    ID_Medicamento INT NOT NULL,
    Descripcion VARCHAR(255),
    Cantidad INT NOT NULL,
    PrecioUnitario DECIMAL(10,2),
    FOREIGN KEY (ID_Factura) REFERENCES Facturas(ID),
    FOREIGN KEY (ID_Medicamento) REFERENCES Medicamentos(ID)
);

CREATE TABLE JornadasEspeciales (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Fecha DATE,
    Descripcion TEXT,
    Especie ENUM("Canino","Felino", "Roedor", "Ave"),
    DemandaEsperada INT NOT NULL,
    ImpactoJornada INT NOT NULL 
);

CREATE TABLE MascotasJornada (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Jornada INT NOT NULL,
    ID_Mascota INT NOT NULL,
    FOREIGN KEY (ID_Jornada) REFERENCES JornadasEspeciales(ID),
    FOREIGN KEY (ID_Mascota) REFERENCES Mascotas(ID)
);

CREATE TABLE Adopciones (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Mascota INT NOT NULL,
    ID_PropietarioNuevo INT NOT NULL,
    Fecha DATE,
    FOREIGN KEY (ID_Mascota) REFERENCES Mascotas(ID),
    FOREIGN KEY (ID_PropietarioNuevo) REFERENCES Propietarios(ID)
);

CREATE TABLE ClubMascotasFrecuentes (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_Propietario INT NOT NULL UNIQUE,
    Nivel VARCHAR(50) DEFAULT 0,
    PuntosAcumulados INT DEFAULT 0,
    FOREIGN KEY (ID_Propietario) REFERENCES Propietarios(ID)
);