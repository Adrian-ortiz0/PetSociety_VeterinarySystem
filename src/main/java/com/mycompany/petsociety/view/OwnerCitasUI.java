package com.mycompany.petsociety.view;
import com.mycompany.petsociety.controllers.ClubController;
import static com.mycompany.petsociety.controllers.ClubController.obtenerMiembrosPorId;
import com.mycompany.petsociety.controllers.DateController;
import com.mycompany.petsociety.controllers.DesparacitantesController;
import com.mycompany.petsociety.controllers.FacturaGenerator;
import com.mycompany.petsociety.controllers.InsumosController;
import com.mycompany.petsociety.controllers.OwnerController;
import com.mycompany.petsociety.controllers.ServiciosController;
import com.mycompany.petsociety.controllers.VacunasController;
import com.mycompany.petsociety.controllers.VeterinarianController;
import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.ClubMember;
import com.mycompany.petsociety.models.Desparacitario;
import com.mycompany.petsociety.models.MaterialMedico;
import com.mycompany.petsociety.models.Medicamento;
import com.mycompany.petsociety.models.Owner;
import com.mycompany.petsociety.models.Servicio;
import com.mycompany.petsociety.models.TipoAntiParasitario;
import com.mycompany.petsociety.models.TipoServicioAdicional;
import com.mycompany.petsociety.models.Vacuna;
import com.mycompany.petsociety.models.Veterinarian;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class OwnerCitasUI {
    private Scanner scanner;

    
    public OwnerCitasUI(Owner owner){
        this.scanner = new Scanner(System.in);
    }
    
    public void menuAdminCitas(Owner owner) {
    OUTER:
    while (true) {
        try {
            System.out.println("==============================================");
            System.out.println("       Menú de Administración de Citas");
            System.out.println("==============================================");
            System.out.println("¿Qué deseas hacer?");
            System.out.println(" 1. Vacunar");
            System.out.println(" 2. Desparacitar");
            System.out.println(" 3. Procedimientos");
            System.out.println(" 4. Cirugías");
            System.out.println(" 5. Consultas");
            System.out.println(" 0. Salir");
            System.out.println("----------------------------------------------");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.println("\nIniciando proceso de vacunación...");
                    gestionVacunacion(owner);
                    break;
                case 2:
                    System.out.println("\nIniciando proceso de desparacitación...");
                    gestionarDesparacitacion(owner);
                    break;
                case 3:
                    System.out.println("\nIniciando procedimientos...");
                    gestionarProcedimientos(owner);
                    break;
                case 4:
                    System.out.println("\nIniciando gestión de cirugías...");
                    gestionarCirugias(owner);
                    break;
                case 5:
                    System.out.println("\nIniciando gestión de consultas...");
                    gestionarConsultas(owner);
                    break;
                case 0:
                    System.out.println("\nSaliendo del sistema de administración...");
                    break OUTER;
                default:
                    System.out.println("\nOpción inválida. Intenta nuevamente.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("\nEntrada no válida. Por favor, ingresa un número.");
        } catch (Exception e) {
            System.out.println("\nOcurrió un error inesperado: " + e.getMessage());
        }
    }
}
    public int obtenerOpcionUsuario(int min, int max, String mensaje) {
        int opcion = -1;
        while (true) {
            try {
                System.out.println(mensaje);
                String input = scanner.nextLine();

                if (input.trim().isEmpty()) {
                    System.out.println("Entrada vacía. Por favor, ingresa un número.");
                    continue;
                }
                opcion = Integer.parseInt(input);

                if (opcion >= min && opcion <= max) {
                    return opcion;
                } else {
                    System.out.println("Opción no válida. Por favor, elige un número entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debes ingresar un número.");
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
    }
    
    public void gestionarConsultas(Owner owner) {
    System.out.println("BIENVENIDO AL SISTEMA DE CONSULTAS");
    System.out.println("--------------------------------------------------");
    System.out.println();

    ArrayList<Servicio> servicios = ServiciosController.mostrarServiciosPorTipo("Consulta_Basica");
    if (servicios.isEmpty()) {
        System.out.println("No hay servicios básicos disponibles");
        return;
    }

    System.out.println("Servicios disponibles:");
    IntStream.range(0, servicios.size())
             .forEach(i -> System.out.println((i + 1) + ". " + servicios.get(i).getNombre() + " - Costo: " + servicios.get(i).getCostoBase()));

    int opcionServicio = obtenerOpcionUsuario(1, servicios.size(), "Elige un servicio");
    Servicio servicioSeleccionado = servicios.get(opcionServicio - 1);
    System.out.println("Has seleccionado: " + servicioSeleccionado.getNombre());

    BigDecimal costoTotal = servicioSeleccionado.getCostoBase();

    ArrayList<Animal> mascotasOwner = OwnerController.obtenerMascotasDePropietario(owner);
    if (mascotasOwner.isEmpty()) {
        System.out.println("No tienes mascotas registradas.");
        return;
    }

    System.out.println("Mascotas Disponibles:");
    IntStream.range(0, mascotasOwner.size())
             .forEach(i -> System.out.println((i + 1) + ". " + mascotasOwner.get(i).getName()));

    int opcionMascota = obtenerOpcionUsuario(1, mascotasOwner.size(), "Elige la mascota para el procedimiento");
    Animal animal = mascotasOwner.get(opcionMascota - 1);
    System.out.println("Has seleccionado a " + animal.getName());

    String especie = animal.getSpecie();
    System.out.println("Especie: " + especie);

    double factorEspecie = switch (especie) {
        case "Canino" -> 1.1;
        case "Felino" -> 1.05;
        case "Roedor" -> 1.0;
        case "Ave" -> 0.9;
        default -> 1.0;
    };

    costoTotal = costoTotal.multiply(BigDecimal.valueOf(factorEspecie));

    ArrayList<Veterinarian> veterinarians = VeterinarianController.obtenerTodosLosVeterinarios();
    if (veterinarians.isEmpty()) {
        System.out.println("No hay veterinarios disponibles.");
        return;
    }

    System.out.println("Veterinarios Disponibles:");
    IntStream.range(0, veterinarians.size())
             .forEach(i -> System.out.println((i + 1) + ". " + veterinarians.get(i).getNombre()));

    int opcionVet = obtenerOpcionUsuario(1, veterinarians.size(), "Elige al veterinario que hará el procedimiento");
    Veterinarian vetElegido = veterinarians.get(opcionVet - 1);
    System.out.println("Veterinario elegido: " + vetElegido.getNombre());

    double nuevoPeso = obtenerDoubleConValidacion("Ingresa el nuevo peso de: " + animal.getName());
    double nuevaAltura = obtenerDoubleConValidacion("Ingresa la nueva altura de: " + animal.getName());

    DateController.insertarNuevasMedidas(animal, nuevoPeso, nuevaAltura, LocalDate.now());
    System.out.println("Medidas registradas con éxito!");

    String motivoConsulta = obtenerTextoConValidacion("Escribe el motivo de la consulta: ");
    String diagnostico = obtenerTextoConValidacion("Escribe el diagnóstico de la mascota: ");
    String tratamientoRecomendado = obtenerTextoConValidacion("Escribe el tratamiento que recomiendas: ");

    ArrayList<Medicamento> medicamentos = InsumosController.mostrarMedicamentosPorTipo(animal.getSpecie());
    ArrayList<Medicamento> medicamentosElegidos = new ArrayList<>();
    while (true) {
        System.out.println("Medicamentos disponibles:");
        IntStream.range(0, medicamentos.size())
                 .forEach(i -> System.out.println((i + 1) + ". " + medicamentos.get(i).getNombre() + " - Costo: " + medicamentos.get(i).getPrecioUnitario()));
        System.out.println("0. Terminar de elegir medicamentos");

        int opcionMedicamento = obtenerOpcionUsuario(0, medicamentos.size(), "Elige un medicamento");
        if (opcionMedicamento == 0) break;

        Medicamento medicamentoSeleccionado = medicamentos.get(opcionMedicamento - 1);
        System.out.println("Has seleccionado: " + medicamentoSeleccionado.getNombre());

        int cantidad = obtenerOpcionUsuario(1, 100, "Cuántas unidades se usaron: ");
        BigDecimal precioTotalMedicamento = medicamentoSeleccionado.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidad));

        boolean stockSuficiente = VacunasController.restarCantidadInsumos(medicamentoSeleccionado.getId(), cantidad);
        if (stockSuficiente) {
            medicamentosElegidos.add(medicamentoSeleccionado);
            costoTotal = costoTotal.add(precioTotalMedicamento);
            System.out.println("Se ha añadido " + cantidad + " unidades de " + medicamentoSeleccionado.getNombre());
        } else {
            System.out.println("No se pudo añadir " + medicamentoSeleccionado.getNombre() + " por falta de stock.");
        }
    }
    
    ArrayList<TipoServicioAdicional> serviciosElegidos = new ArrayList<>();
    while (true) {
        System.out.println("Servicios adicionales disponibles:");
        ArrayList<TipoServicioAdicional> serviciosAdicionales = ServiciosController.mostrarServiciosAdicionales();
        IntStream.range(0, serviciosAdicionales.size())
                 .forEach(i -> System.out.println((i + 1) + ". " + serviciosAdicionales.get(i).getNombre() + " - Costo: " + serviciosAdicionales.get(i).getPrecioBase()));
        System.out.println("0. Terminar de elegir servicios adicionales");

        int opcionServicioAdicional = obtenerOpcionUsuario(0, serviciosAdicionales.size(), "Elige un servicio adicional: ");
        if (opcionServicioAdicional == 0) break;

        TipoServicioAdicional servicioSeleccionadoAdicional = serviciosAdicionales.get(opcionServicioAdicional - 1);
        System.out.println("Has seleccionado: " + servicioSeleccionadoAdicional.getNombre());
        
        if (!serviciosElegidos.contains(servicioSeleccionadoAdicional)) {
            serviciosElegidos.add(servicioSeleccionadoAdicional);
            costoTotal = costoTotal.add(BigDecimal.valueOf(servicioSeleccionadoAdicional.getPrecioBase()));
            System.out.println(servicioSeleccionadoAdicional.getNombre() + " se ha añadido a tus servicios.");
        } else {
            System.out.println("Este servicio ya ha sido seleccionado anteriormente.");
        }
    }

    ClubMember miembro = obtenerMiembrosPorId(owner);
    if (miembro != null && miembro.getDiscountStrategy() != null) {
        double porcentajeDescuento = miembro.getDiscountStrategy().calcularDescuento(miembro.getAccumulatedPoints());
        BigDecimal descuento = costoTotal.multiply(BigDecimal.valueOf(porcentajeDescuento));
        costoTotal = costoTotal.subtract(descuento);
        System.out.println("Descuento aplicado: " + descuento);
        ClubController.sumarPuntosPorConsulta(miembro);
        System.out.println("¡Más 10 puntos para: " + owner.getName() + "!");
    }

    System.out.println("Costo total de la consulta: " + costoTotal);
    
    ServiciosController.insertarServicioConHistorial(opcionServicio, opcionVet, opcionMascota, LocalDate.now(), costoTotal, owner, motivoConsulta);
    FacturaGenerator.generarFacturaPDFConsulta(
        owner.getName(), 
        owner.getCedula(), 
        animal.getName(), 
        especie, 
        vetElegido.getNombre(), 
        motivoConsulta, 
        diagnostico, 
        tratamientoRecomendado, 
        costoTotal, 
        medicamentosElegidos, 
        serviciosElegidos
    );
    

}

    private double obtenerDoubleConValidacion(String mensaje) {
        double valor = -1;
        while (true) {
            try {
                System.out.println(mensaje);
                valor = Double.parseDouble(scanner.nextLine());
                if (valor <= 0) {
                    System.out.println("El valor debe ser mayor que cero.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debes ingresar un número.");
            }
        }
    }

    private String obtenerTextoConValidacion(String mensaje) {
        String input;
        while (true) {
            System.out.println(mensaje);
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("La entrada no puede estar vacía.");
            } else {
                return input;
            }
        }
    }
    
    public void gestionarCirugias(Owner owner) {
        try {
            System.out.println("BIENVENIDO AL SISTEMA DE CIRUGIAS");
            System.out.println("----------------------------------------------");
            System.out.println();
            ArrayList<Servicio> servicios = ServiciosController.mostrarServiciosPorTipo("Cirugia");

            if (servicios.isEmpty()) {
                System.out.println("No hay servicios de cirugía disponibles.");
                return;
            }

            System.out.println("Servicios de Cirugía Disponibles:");
            for (int i = 0; i < servicios.size(); i++) {
                Servicio servicio = servicios.get(i);
                System.out.println((i + 1) + ". " + servicio.getNombre() + " - Costo: " + servicio.getCostoBase());
            }

            int opcionServicio = obtenerOpcionUsuario(1, servicios.size(), "Elige un servicio de cirugía");
            Servicio servicioSeleccionado = servicios.get(opcionServicio - 1);
            System.out.println("Has seleccionado: " + servicioSeleccionado.getNombre());

            ArrayList<Animal> mascotasOwner = OwnerController.obtenerMascotasDePropietario(owner);
            if (mascotasOwner.isEmpty()) {
                System.out.println("No tienes mascotas registradas.");
                return;
            }

            System.out.println("Mascotas Disponibles:");
            for (int i = 0; i < mascotasOwner.size(); i++) {
                Animal animal = mascotasOwner.get(i);
                System.out.println((i + 1) + ". " + animal.getName());
            }

            int opcionMascota = obtenerOpcionUsuario(1, mascotasOwner.size(), "Elige la mascota para el procedimiento");
            Animal animal = mascotasOwner.get(opcionMascota - 1);
            System.out.println("Has seleccionado a " + animal.getName());

            ArrayList<Veterinarian> veterinarians = VeterinarianController.obtenerTodosLosVeterinarios();
            if (veterinarians.isEmpty()) {
                System.out.println("No hay veterinarios disponibles.");
                return;
            }

            System.out.println("Veterinarios Disponibles:");
            for (int i = 0; i < veterinarians.size(); i++) {
                Veterinarian vet = veterinarians.get(i);
                System.out.println((i + 1) + ". " + vet.getNombre());
            }

            int opcionVet = obtenerOpcionUsuario(1, veterinarians.size(), "Elige al veterinario que hará el procedimiento");
            Veterinarian vetElegido = veterinarians.get(opcionVet - 1);
            System.out.println("Veterinario elegido: " + vetElegido.getNombre());

            System.out.println("Medicamentos disponibles:");
            ArrayList<Medicamento> medicamentos = InsumosController.mostrarMedicamentosPorTipo(animal.getSpecie());
            ArrayList<Medicamento> medicamentosElegidos = new ArrayList<>();

            while (true) {
                System.out.println("Medicamentos:");
                for (int i = 0; i < medicamentos.size(); i++) {
                    Medicamento medicamento = medicamentos.get(i);
                    System.out.println((i + 1) + ". " + medicamento.getNombre() + " - Costo: " + medicamento.getPrecioUnitario());
                }
                System.out.println("0. Terminar de elegir medicamentos");

                int opcionMedicamento = obtenerOpcionUsuario(0, medicamentos.size(), "Elige un medicamento");
                if (opcionMedicamento == 0) break;

                Medicamento medicamentoSeleccionado = medicamentos.get(opcionMedicamento - 1);
                System.out.println("Has seleccionado: " + medicamentoSeleccionado.getNombre());

                int cantidad = obtenerOpcionUsuario(1, 100, "Cuántas unidades deseas elegir");
                boolean stockSuficiente = VacunasController.restarCantidadInsumos(medicamentoSeleccionado.getId(), cantidad);
                if (stockSuficiente) {
                    medicamentosElegidos.add(medicamentoSeleccionado);
                    System.out.println("Se ha añadido " + cantidad + " unidades de " + medicamentoSeleccionado.getNombre());
                } else {
                    System.out.println("No se pudo añadir " + medicamentoSeleccionado.getNombre() + " por falta de stock.");
                }
            }

            System.out.println("Materiales médicos disponibles:");
            ArrayList<MaterialMedico> materialesMedicos = InsumosController.mostrarMaterialesMedicos();
            ArrayList<MaterialMedico> materialesElegidos = new ArrayList<>();

            while (true) {
                System.out.println("Materiales Médicos:");
                for (int i = 0; i < materialesMedicos.size(); i++) {
                    MaterialMedico materialMedico = materialesMedicos.get(i);
                    System.out.println((i + 1) + ". " + materialMedico.getNombre() + " - Stock: " + materialMedico.getCantidad() + " - Costo: " + materialMedico.getPrecioUnitario());
                }
                System.out.println("0. Terminar de elegir materiales médicos");

                int opcionMaterial = obtenerOpcionUsuario(0, materialesMedicos.size(), "Elige un material médico");
                if (opcionMaterial == 0) break;

                MaterialMedico materialSeleccionado = materialesMedicos.get(opcionMaterial - 1);
                System.out.println("Has seleccionado: " + materialSeleccionado.getNombre());

                int cantidad = obtenerOpcionUsuario(1, 100, "Cuántas unidades deseas elegir");
                boolean stockSuficiente = VacunasController.restarCantidadInsumos(materialSeleccionado.getId(), cantidad);
                if (stockSuficiente) {
                    materialesElegidos.add(materialSeleccionado);
                    System.out.println("Se ha añadido " + cantidad + " unidades de " + materialSeleccionado.getNombre());
                } else {
                    System.out.println("No se pudo añadir " + materialSeleccionado.getNombre() + " por falta de stock.");
                }
            }

            BigDecimal costoBase = servicioSeleccionado.getCostoBase();
            BigDecimal costoAdicionalAnimal = calcularCostoAnimal(animal.getSpecie());
            BigDecimal costoMedicamentos = calcularCostoMedicamentos(medicamentosElegidos);
            BigDecimal costoMateriales = calcularCostoMateriales(materialesElegidos);
            BigDecimal costoFinal = costoBase.multiply(costoAdicionalAnimal).add(costoMedicamentos).add(costoMateriales);
            ClubMember miembro = obtenerMiembrosPorId(owner);

            if (miembro != null && miembro.getDiscountStrategy() != null) {
                double porcentajeDescuento = miembro.getDiscountStrategy().calcularDescuento(miembro.getAccumulatedPoints());
                BigDecimal porcentajeDescuentoBigDecimal = BigDecimal.valueOf(porcentajeDescuento);
                BigDecimal descuento = costoFinal.multiply(porcentajeDescuentoBigDecimal);
                costoFinal = costoFinal.subtract(descuento);
                System.out.println("Descuento aplicado: " + descuento);
                ClubController.sumarPuntosPorConsulta(miembro);
                System.out.println("¡Más 10 puntos para: " + owner.getName() + "!");
            }

            System.out.println("Costo final del procedimiento: " + costoFinal);

            int servicioID = servicioSeleccionado.getId();
            int mascotaID = animal.getId();
            int veterinarioID = vetElegido.getId();
            LocalDate fechaServicio = LocalDate.now();

            boolean servicioInsertado = ServiciosController.insertarServicioConHistorial(servicioID, mascotaID, veterinarioID, fechaServicio, costoFinal, owner, "Cirugia");

            if (servicioInsertado) {
                System.out.println("El servicio ha sido registrado correctamente.");
            } else {
                System.out.println("Hubo un error al registrar el servicio.");
            }

            FacturaGenerator.generarFacturaPDFProcedimiento(
                owner.getName(),
                owner.getCedula(),
                animal.getName(),
                animal.getSpecie(),
                vetElegido.getNombre(),
                servicioSeleccionado.getNombre(),
                costoBase,
                costoFinal,
                medicamentosElegidos, 
                materialesElegidos  
            );

        } catch (Exception e) {
            System.out.println("Se ha producido un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void gestionarProcedimientos(Owner owner) {
        try {
            System.out.println("BIENVENIDO AL SISTEMA DE PROCEDIMIENTOS");
            System.out.println("-----------------------------------------------");
            System.out.println();

            ArrayList<Servicio> servicios = ServiciosController.mostrarServiciosPorTipo("Procedimiento_Especial");
            for (int i = 0; i < servicios.size(); i++) {
                Servicio servicio = servicios.get(i);
                System.out.println((i + 1) + ". " + servicio.getNombre() + " - Costo: " + servicio.getCostoBase());
            }

            int opcionServicio = obtenerOpcionUsuario(1, servicios.size(), "Elige un servicio");
            Servicio servicioSeleccionado = servicios.get(opcionServicio - 1);
            System.out.println("Has seleccionado: " + servicioSeleccionado.getNombre());

            ArrayList<Animal> mascotasOwner = OwnerController.obtenerMascotasDePropietario(owner);
            System.out.println("Cantidad de mascotas: " + mascotasOwner.size());
            if (mascotasOwner.isEmpty()) {
                System.out.println("No tienes mascotas registradas.");
                return;
            }

            for (int i = 0; i < mascotasOwner.size(); i++) {
                Animal mascota = mascotasOwner.get(i);
                System.out.println((i + 1) + ". " + mascota.getName());
            }

            int opcionMascota = obtenerOpcionUsuario(1, mascotasOwner.size(), "¿Que mascota deseas tratar?");
            Animal animal = mascotasOwner.get(opcionMascota - 1);
            System.out.println("Has seleccionado a " + animal.getName());

            ArrayList<Veterinarian> veterinarians = VeterinarianController.obtenerTodosLosVeterinarios();
            if (veterinarians.isEmpty()) {
                System.out.println("No hay veterinarios disponibles.");
                return;
            }

            for (int i = 0; i < veterinarians.size(); i++) {
                Veterinarian veterinario = veterinarians.get(i);
                System.out.println((i + 1) + ". " + veterinario.getNombre());
            }

            int opcionVet = obtenerOpcionUsuario(1, veterinarians.size(), "Elige al veterinario que hara el procedimiento: ");
            Veterinarian vetElegido = veterinarians.get(opcionVet - 1);
            System.out.println("Veterinario elegido: " + vetElegido.getNombre());

            System.out.println("Que medicamentos usara el veterinario: ");
            ArrayList<Medicamento> medicamentos = InsumosController.mostrarMedicamentosPorTipo(animal.getSpecie());
            ArrayList<Medicamento> medicamentosElegidos = new ArrayList<>();
            while (true) {
                try {
                    for (int i = 0; i < medicamentos.size(); i++) {
                        Medicamento medicamento = medicamentos.get(i);
                        System.out.println((i + 1) + ". " + medicamento.getNombre() + " - Costo: " + medicamento.getPrecioUnitario());
                    }
                    System.out.println("0. Terminar de elegir medicamentos");
                    int opcionMedicamento = obtenerOpcionUsuario(0, medicamentos.size(), "Elige un medicamento: ");
                    if (opcionMedicamento == 0) break;

                    Medicamento medicamentoSeleccionado = medicamentos.get(opcionMedicamento - 1);
                    System.out.println("Has seleccionado: " + medicamentoSeleccionado.getNombre());

                    int cantidad = obtenerOpcionUsuario(1, Integer.MAX_VALUE, "¿Cuantas unidades deseas elegir?");
                    boolean stockSuficiente = VacunasController.restarCantidadInsumos(medicamentoSeleccionado.getId(), cantidad);
                    if (stockSuficiente) {
                        medicamentosElegidos.add(medicamentoSeleccionado);
                        System.out.println("Se ha añadido " + cantidad + " unidades de " + medicamentoSeleccionado.getNombre());
                    } else {
                        System.out.println("No se pudo añadir " + medicamentoSeleccionado.getNombre() + " por falta de stock.");
                    }
                } catch (Exception e) {
                    System.out.println("Error al seleccionar medicamentos: " + e.getMessage());
                    continue; 
                }
            }

            System.out.println("Que materiales medicos usara el veterinario: ");
            ArrayList<MaterialMedico> materialesMedicos = InsumosController.mostrarMaterialesMedicos();
            ArrayList<MaterialMedico> materialesElegidos = new ArrayList<>();
            while (true) {
                try {
                    for (int i = 0; i < materialesMedicos.size(); i++) {
                        MaterialMedico materialMedico = materialesMedicos.get(i);
                        System.out.println((i + 1) + ". " + materialMedico.getNombre() + " - Stock: " + materialMedico.getCantidad() + " - Costo: " + materialMedico.getPrecioUnitario());
                    }
                    System.out.println("0. Terminar de elegir materiales medicos");
                    int opcionMaterial = obtenerOpcionUsuario(0, materialesMedicos.size(), "Elige un material medico: ");
                    if (opcionMaterial == 0) break;

                    MaterialMedico materialSeleccionado = materialesMedicos.get(opcionMaterial - 1);
                    System.out.println("Has seleccionado: " + materialSeleccionado.getNombre());

                    int cantidad = obtenerOpcionUsuario(1, Integer.MAX_VALUE, "¿Cuantas unidades deseas elegir?");
                    boolean stockSuficiente = VacunasController.restarCantidadInsumos(materialSeleccionado.getId(), cantidad);
                    if (stockSuficiente) {
                        materialesElegidos.add(materialSeleccionado);
                        System.out.println("Se ha añadido " + cantidad + " unidades de " + materialSeleccionado.getNombre());
                    } else {
                        System.out.println("No se pudo añadir " + materialSeleccionado.getNombre() + " por falta de stock.");
                    }
                } catch (Exception e) {
                    System.out.println("Error al seleccionar materiales médicos: " + e.getMessage());
                    continue; 
                }
            }

            BigDecimal costoBase = servicioSeleccionado.getCostoBase();
            BigDecimal costoAdicionalAnimal = calcularCostoAnimal(animal.getSpecie());
            BigDecimal costoMedicamentos = calcularCostoMedicamentos(medicamentosElegidos);
            BigDecimal costoMateriales = calcularCostoMateriales(materialesElegidos);
            BigDecimal costoFinal = costoBase.multiply(costoAdicionalAnimal).add(costoMedicamentos).add(costoMateriales);

            ClubMember miembro = obtenerMiembrosPorId(owner);
            if (miembro != null && miembro.getDiscountStrategy() != null) {
                try {
                    double porcentajeDescuento = miembro.getDiscountStrategy().calcularDescuento(miembro.getAccumulatedPoints());
                    BigDecimal porcentajeDescuentoBigDecimal = BigDecimal.valueOf(porcentajeDescuento);
                    BigDecimal descuento = costoFinal.multiply(porcentajeDescuentoBigDecimal);
                    costoFinal = costoFinal.subtract(descuento);
                    System.out.println("Descuento aplicado: " + descuento);
                    ClubController.sumarPuntosPorConsulta(miembro);
                    System.out.println("Mas 10 puntos para: " + owner.getName() + "!");
                } catch (Exception e) {
                    System.out.println("Error al aplicar el descuento: " + e.getMessage());
                }
            }
            System.out.println("Costo final del procedimiento: " + costoFinal);

            int servicioID = servicioSeleccionado.getId();
            int mascotaID = animal.getId();
            int veterinarioID = vetElegido.getId();
            LocalDate fechaServicio = LocalDate.now();

            boolean servicioInsertado = ServiciosController.insertarServicioConHistorial(servicioID, mascotaID, veterinarioID, fechaServicio, costoFinal, owner, "Procedimiento");

            if (servicioInsertado) {
                System.out.println("El servicio ha sido registrado correctamente.");
            } else {
                System.out.println("Hubo un error al registrar el servicio.");
            }

            FacturaGenerator.generarFacturaPDFProcedimiento(
                owner.getName(),
                owner.getCedula(),
                animal.getName(),
                animal.getSpecie(),
                vetElegido.getNombre(),
                servicioSeleccionado.getNombre(),
                costoBase,
                costoFinal,
                medicamentosElegidos, 
                materialesElegidos  
            );
        } catch (Exception e) {
            System.out.println("Ocurrió un error general: " + e.getMessage());
        }
    }

private BigDecimal calcularCostoAnimal(String especie) {
    switch (especie.toLowerCase()) {
        case "caninos": return new BigDecimal("10.0");
        case "felinos": return new BigDecimal("10.0");
        case "roedores": return new BigDecimal("5.0");
        case "aves": return new BigDecimal("7.0");
        default: return BigDecimal.ZERO;
    }
}

private BigDecimal calcularCostoMedicamentos(ArrayList<Medicamento> medicamentos) {
    BigDecimal costoTotal = BigDecimal.ZERO;
    for (Medicamento medicamento : medicamentos) {
        costoTotal = costoTotal.add(medicamento.getPrecioUnitario());
    }
    return costoTotal;
}

private BigDecimal calcularCostoMateriales(ArrayList<MaterialMedico> materiales) {
    BigDecimal costoTotal = BigDecimal.ZERO;
    for (MaterialMedico material : materiales) {
        costoTotal = costoTotal.add(material.getPrecioUnitario());
    }
    return costoTotal;
}
    
    
    public void gestionarDesparacitacion(Owner owner) {
    try {
        System.out.println("BIENVENIDO AL SISTEMA DE DESPARACITACION");
        System.out.println("---------------------------------------------------");
        System.out.println();

        ArrayList<Servicio> servicios = ServiciosController.mostrarServiciosPorTipo("Desparacitacion");

        if (servicios.isEmpty()) {
            System.out.println("No hay servicios de desparacitación disponibles.");
            return;
        }

        System.out.println("Servicios disponibles:");
        for (int i = 0; i < servicios.size(); i++) {
            Servicio servicio = servicios.get(i);
            System.out.println((i + 1) + ". " + servicio.getNombre() + " - Costo: " + servicio.getCostoBase());
        }

        int opcionServicio = obtenerOpcionUsuario(1, servicios.size(), "Selecciona el servicio de desparacitación que deseas");
        if (opcionServicio < 1 || opcionServicio > servicios.size()) {
            System.out.println("Opción inválida.");
            return;
        }
        
        Servicio servicioSeleccionado = servicios.get(opcionServicio - 1);
        System.out.println("Has seleccionado el servicio: " + servicioSeleccionado.getNombre());

        ArrayList<Animal> mascotasOwner = OwnerController.obtenerMascotasDePropietario(owner);
        if (mascotasOwner.isEmpty()) {
            System.out.println("No tienes mascotas registradas.");
            return;
        }

        System.out.println("Estas son tus mascotas:");
        for (int i = 0; i < mascotasOwner.size(); i++) {
            Animal mascota = mascotasOwner.get(i);
            System.out.println((i + 1) + ". " + mascota.getName());
        }

        int opcionMascota = obtenerOpcionUsuario(1, mascotasOwner.size(), "¿Qué mascota deseas desparacitar?");
        Animal animal = mascotasOwner.get(opcionMascota - 1);
        System.out.println("Has seleccionado a " + animal.getName());

        ArrayList<Veterinarian> veterinarians = VeterinarianController.obtenerTodosLosVeterinarios();
        if (veterinarians.isEmpty()) {
            System.out.println("No hay veterinarios disponibles.");
            return;
        }

        System.out.println("Estos son los veterinarios disponibles para el procedimiento:");
        for (int i = 0; i < veterinarians.size(); i++) {
            Veterinarian veterinario = veterinarians.get(i);
            System.out.println((i + 1) + ". " + veterinario.getNombre());
        }

        int opcionVet = obtenerOpcionUsuario(1, veterinarians.size(), "Elige al veterinario que hará el procedimiento: ");
        Veterinarian vetElegido = veterinarians.get(opcionVet - 1);
        System.out.println("Veterinario elegido: " + vetElegido.getNombre());

        System.out.println("¿Qué tipo de desparacitación deseas?");
        TipoAntiParasitario[] tipos = TipoAntiParasitario.values();

        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }

        int opcionTipo = obtenerOpcionUsuario(1, tipos.length, "Selecciona el tipo de desparacitación");
        TipoAntiParasitario tipoSeleccionado = tipos[opcionTipo - 1];
        System.out.println("Has seleccionado: " + tipoSeleccionado);

        ArrayList<Desparacitario> desparacitarios = DesparacitantesController.mostrarDesparacitariosPorTipo(tipoSeleccionado);

        if (desparacitarios.isEmpty()) {
            System.out.println("No hay desparacitarios disponibles para este tipo.");
            return;
        }

        System.out.println("Estos son los desparacitarios disponibles para " + tipoSeleccionado + ":");
        for (int i = 0; i < desparacitarios.size(); i++) {
            Desparacitario desp = desparacitarios.get(i);
            System.out.println((i + 1) + ". " + desp.getNombre());
        }

        int opcionDesparacitario = obtenerOpcionUsuario(1, desparacitarios.size(), "Selecciona el desparacitario que deseas usar");
        Desparacitario desparacitarioSeleccionado = desparacitarios.get(opcionDesparacitario - 1);
        System.out.println("Has seleccionado: " + desparacitarioSeleccionado.getNombre());

        BigDecimal costoBase = BigDecimal.valueOf(10.0);
        String especie = animal.getSpecie().toLowerCase();
        BigDecimal incrementoPorEspecie = BigDecimal.ZERO;

        switch (especie) {
            case "canino":
                incrementoPorEspecie = BigDecimal.valueOf(10.0);
                break;
            case "felino":
                incrementoPorEspecie = BigDecimal.valueOf(10.0);
                break;
            case "roedor":
                incrementoPorEspecie = BigDecimal.valueOf(5.0);
                break;
            case "ave":
                incrementoPorEspecie = BigDecimal.valueOf(7.0);
                break;
            default:
                System.out.println("Especie desconocida, no se aplicó incremento.");
                break;
        }

        costoBase = costoBase.multiply(incrementoPorEspecie);
        BigDecimal costoFinal = costoBase.add(desparacitarioSeleccionado.getPrecioUnitario());

        try {
            DesparacitantesController.insertarServicioConHistorial(
                servicioSeleccionado.getId(),
                animal.getId(),
                vetElegido.getId(),
                LocalDate.now(),
                costoFinal,
                owner
            );
        } catch (Exception e) {
            System.out.println("Hubo un error al registrar el servicio: " + e.getMessage());
            return; 
        }

        try {
            FacturaGenerator.generarFacturaPDF(
                owner.getName(),
                owner.getCedula(),
                animal.getName(),
                animal.getSpecie(),
                vetElegido.getNombre(),
                servicioSeleccionado.getNombre(),
                costoBase,
                costoFinal,
                desparacitarioSeleccionado.getNombre()
            );
        } catch (Exception e) {
            System.out.println("Hubo un error al generar la factura: " + e.getMessage());
        }

        try {
            VacunasController.restarUnidadVacuna(desparacitarioSeleccionado.getId());
        } catch (Exception e) {
            System.out.println("Hubo un error al restar la unidad de vacuna: " + e.getMessage());
        }

    } catch (Exception e) {
        System.out.println("Ocurrió un error general: " + e.getMessage());
    }
}

    
    public void gestionVacunacion(Owner owner) {
    try {
        System.out.println("BIENVENIDO AL SISTEMA DE VACUNACION");
        System.out.println("--------------------------------------------------");
        System.out.println();
        ArrayList<Animal> mascotasOwner = OwnerController.obtenerMascotasDePropietario(owner);

        if (mascotasOwner.isEmpty()) {
            System.out.println("No tienes mascotas registradas.");
            return;
        }

        for (int i = 0; i < mascotasOwner.size(); i++) {
            Animal mascota = mascotasOwner.get(i);
            System.out.println((i + 1) + ". " + mascota.getName());
        }

        int opcionMascota = obtenerOpcionUsuario(1, mascotasOwner.size(), "Que mascota deseas vacunar?");
        Animal animal = mascotasOwner.get(opcionMascota - 1);
        System.out.println("Has seleccionado a " + animal.getName());

        ArrayList<Vacuna> vacunas = VacunasController.mostrarTodasLasVacunasPorEspecie(animal);
        if (vacunas.isEmpty()) {
            System.out.println("No hay vacunas disponibles para esta especie.");
            return;
        }

        for (int i = 0; i < vacunas.size(); i++) {
            Vacuna vacuna = vacunas.get(i);
            System.out.println((i + 1) + ". " + vacuna.getNombre());
        }

        int opcionVacuna = obtenerOpcionUsuario(1, vacunas.size(), "Que vacuna deseas aplicar?");
        Vacuna vacuna = vacunas.get(opcionVacuna - 1);
        System.out.println("Has seleccionado la vacuna: " + vacuna.getNombre());

        ArrayList<Veterinarian> veterinarians = VeterinarianController.obtenerTodosLosVeterinarios();
        if (veterinarians.isEmpty()) {
            System.out.println("No hay veterinarios disponibles.");
            return;
        }

        for (int i = 0; i < veterinarians.size(); i++) {
            Veterinarian veterinario = veterinarians.get(i);
            System.out.println((i + 1) + ". " + veterinario.getNombre());
        }

        int opcionVet = obtenerOpcionUsuario(1, veterinarians.size(), "Elige al veterinario que hara el procedimiento: ");
        Veterinarian vetElegido = veterinarians.get(opcionVet - 1);
        System.out.println("Veterinario elegido: " + vetElegido.getNombre());

        boolean actualizacionExitosa = VacunasController.restarUnidadVacuna(vacuna.getId());
        if (!actualizacionExitosa) {
            System.out.println("Hubo un problema al restar la unidad de la vacuna.");
            return;
        }

        System.out.println("Se ha restado una unidad de la vacuna: " + vacuna.getNombre());

        try {
            while (true) {
                System.out.println("Introduce la fecha de la proxima dosis (formato: yyyy-mm-dd): ");
                String fechaEntrada = scanner.nextLine();
                LocalDate proximaDosisLocalDate = LocalDate.parse(fechaEntrada);

                if (proximaDosisLocalDate.isAfter(LocalDate.now())) {
                    boolean registroHistorial = VacunasController.registrarAplicacionVacuna(
                        animal.getId(),
                        vacuna.getId(),
                        vetElegido.getId(),
                        LocalDate.now(),
                        proximaDosisLocalDate
                    );

                    if (registroHistorial) {
                        System.out.println("La vacunacion ha sido registrada correctamente en el historial.");
                        break;
                    } else {
                        System.out.println("Hubo un problema al registrar la vacunacion en el historial.");
                    }
                } else {
                    System.out.println("La fecha de la proxima dosis debe ser posterior a la fecha actual. Intenta de nuevo.");
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha invalido. Use yyyy-mm-dd.");
        }
    } catch (Exception e) {
        System.out.println("Ocurrió un error inesperado: " + e.getMessage());
    }
}
}
