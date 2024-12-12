# VETERINARIA PET SOCIETY 🐶

## 📋 Tabla de Contenidos

1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Funcionalidades Principales](#funcionalidades-principales)
    - [Gestión de Mascotas y Dueños](#1-gestión-de-mascotas-y-dueños)
    - [Gestión de Salud y Vacunación](#2-gestión-de-salud-y-vacunación)
    - [Gestión de Inventarios y Medicamentos](#3-gestión-de-inventarios-y-medicamentos)
    - [Agenda de Servicios Veterinarios](#4-agenda-de-servicios-veterinarios)
    - [Factura Electrónica en Imagen](#5-factura-electrónica-en-imagen)
    - [Reportes y Análisis](#6-reportes-y-análisis)
    - [Actividades Especiales](#7-actividades-especiales)
    - [Interfaces de Usuario y Arquitectura](#8-interfaces-de-usuario-y-arquitectura)
3. [Requerimientos No Funcionales](#requerimientos-no-funcionales)
4. [Instalación](#🚀-instalación)
5. [Preguntas Frecuentes](#❓-preguntas-frecuentes)
6. [Licencia](#📝-licencia)

---

## Descripción del Proyecto 📐
Este proyecto tiene como objetivo desarrollar un sistema que permita gestionar eficientemente las operaciones de una clínica veterinaria. El sistema estará diseñado para manejar la información de las mascotas, sus dueños, historial de salud, inventario de medicamentos y vacunas, agenda de consultas y procedimientos médicos, así como la facturación y servicios adicionales.

---

## Funcionalidades Principales 📱

### 1. Gestión de Mascotas y Dueños
#### 1.1. Registro de Mascotas
Cada mascota registrada en el sistema contará con la siguiente información:
- **Datos básicos**: Nombre, especie (perro, gato, ave, etc.), raza, edad, fecha de nacimiento, sexo.
- **Información médica**: Historial de vacunas, alergias conocidas, condiciones preexistentes, peso y medidas relevantes, procedimientos realizados.
- **Identificación única**: Microchip o tatuaje (si aplica), foto de la mascota.

#### 1.2. Registro de Dueños 🧔
Cada dueño podrá registrar una o varias mascotas con:
- Nombre completo, identificación, dirección, teléfono y correo electrónico.
- Información de contacto de emergencia (opcional).
- Historial de visitas al centro veterinario.

#### 1.3. Relación Mascota-Dueño
- Asociación de cada mascota con un dueño registrado.
- Transferencia de responsabilidad en casos de adopción o venta.

### 2. Gestión de Salud y Vacunación
#### 2.1. Vacunación y Desparasitación
- Registro de historial completo de vacunas (tipo, fabricante, lote, fechas).
- Tratamientos antiparasitarios.
- Alertas automáticas para vacunas y desparasitaciones próximas.

#### 2.2. Consultas Médicas
- Fecha y hora de la consulta.
- Veterinario asignado.
- Motivo de la visita, diagnóstico, recomendaciones y prescripciones.

#### 2.3. Cirugías y Procedimientos Especiales
- Información preoperatoria y detalle del procedimiento.
- Seguimiento postoperatorio.

### 3. Gestión de Inventarios y Medicamentos
#### 3.1. Inventario de Insumos Médicos
- Registro de medicamentos, vacunas y materiales médicos.
- Control de stock y fechas de vencimiento.

#### 3.2. Alertas de Vencimiento
- Notificaciones automáticas para productos cercanos a vencer.

#### 3.3. Reabastecimiento
- Generación de órdenes de compra automáticas según niveles mínimos.
- Registro de proveedores.

### 4. Agenda de Servicios Veterinarios
#### 4.1. Citas y Agenda Médica
- Gestión de citas con detalles de fecha, hora, motivo y estado.

#### 4.2. Servicios Adicionales
- Baño, peluquería, guardería y adiestramiento con registro detallado.

### 5. Factura Electrónica en Imagen
El sistema generará facturas en formato PDF o PNG cumpliendo con la normativa de la DIAN (Resolución 042 de 2020).
- Datos del centro veterinario y del cliente.
- Detalles del servicio o producto.
- Información fiscal (impuestos, CUFE, código QR).

### 6. Reportes y Análisis
- Mascotas atendidas y procedimientos realizados.
- Servicios más solicitados.
- Desempeño del equipo veterinario.
- Inventario y facturación detallada.

### 7. Actividades Especiales
#### 7.1. Días de Adopción
- Registro de mascotas en adopción y contratos firmados.
- Seguimiento postadopción.

#### 7.2. Jornadas de Vacunación y Esterilización Masiva
- Registro de mascotas atendidas y reportes de impacto social.

#### 7.3. Club de Mascotas Frecuentes
- Beneficios exclusivos para clientes frecuentes.
- Sistema de puntos por servicios adquiridos.

### 8. Interfaces de Usuario y Arquitectura
- Diseño basado en el modelo MVC.
- Interfaces amigables e intuitivas para estudiantes, docentes y administradores.

---

## Requerimientos No Funcionales
- Uso de **JDK 17 de Java** y **MySQL** para el backend.
- Implementación de principios **SOLID** y patrones de diseño.
- Uso de colecciones de datos, expresiones lambda y API Stream.
- Manejo de errores con generación de logs almacenados en un archivo en la ruta principal del proyecto.

---

## 🚀 Instalación
1. Clonar este repositorio:
   ```bash
   git clone https://github.com/tu_usuario/veterinary-system.git
   ```
2. Configurar la base de datos MySQL.
3. Asegurarse de tener **JDK 17** instalado.
4. Compilar y ejecutar el proyecto:
   ```bash
   javac Main.java
   java Main
   ```

---

## ❓ Preguntas Frecuentes
**1. ¿Puedo usar este sistema en cualquier clínica veterinaria?**
Sí, está diseñado para ser adaptable a cualquier clínica con modificaciones mínimas.

**2. ¿Requiere internet para funcionar?**
No, el sistema puede ejecutarse localmente, aunque algunas funciones como la facturación electrónica pueden necesitar conexión a internet.

**3. ¿Es posible agregar nuevas funcionalidades?**
Sí, el sistema está diseñado con principios SOLID que facilitan la extensibilidad.

---

## 📝 Licencia
Este proyecto está licenciado bajo la [Licencia MIT](LICENSE). Puedes usarlo, modificarlo y distribuirlo libremente.

---

¡Gracias por elegir nuestro sistema! 🐶🐱 Tu clínica veterinaria estará en las mejores manos.
