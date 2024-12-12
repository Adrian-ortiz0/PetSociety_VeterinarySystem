package com.mycompany.petsociety.view;

import com.mycompany.petsociety.controllers.DateController;
import com.mycompany.petsociety.models.Admin;
import com.mycompany.petsociety.models.Cita;
import com.mycompany.petsociety.models.EstadoCita;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminCitasUI {
    private Scanner scanner;
    
    public AdminCitasUI(Admin admin){
        this.scanner = new Scanner(System.in);
    }
    
    public void menuCitasAdmin(Admin admin) {
        OUTER:
        while (true) {
            System.out.println("=====================================");
            System.out.println("           MENÚ ADMIN CITAS          ");
            System.out.println("=====================================");
            System.out.println("¿Qué deseas hacer?");
            System.out.println("  1. Ver todas las citas");
            System.out.println("  2. Modificar estado de cita");
            System.out.println("  0. Salir");
            System.out.println("=====================================");
            System.out.print("Selecciona una opción: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 0:
                        System.out.println("-------------------------------------");
                        System.out.println("            Saliendo...              ");
                        System.out.println("-------------------------------------");
                        break OUTER;
                    case 1:
                        verTodasLasCitas();
                        break;
                    case 2:
                        editarEstadoCita();
                        break;
                    default:
                        System.out.println("-------------------------------------");
                        System.out.println("      Opción no válida. Intente de nuevo.      ");
                        System.out.println("-------------------------------------");
                }
            } catch (NumberFormatException e) {
                System.out.println("-------------------------------------");
                System.out.println(" Entrada inválida. Por favor ingrese un número válido.");
                System.out.println("-------------------------------------");
            }
        }
    }

    public void editarEstadoCita() {
        System.out.println("=====================================");
        System.out.println("      EDITAR ESTADO DE LA CITA       ");
        System.out.println("=====================================");
        System.out.println("Elija alguna cita para cambiar su estado: ");
        ArrayList<Cita> citas = DateController.obtenerCitas();

        if (citas.isEmpty()) {
            System.out.println("-------------------------------------");
            System.out.println("        No hay citas registradas.     ");
            System.out.println("-------------------------------------");
            return;
        }

        System.out.println("Listado de todas las citas: ");
        for (int i = 0; i < citas.size(); i++) {
            Cita citaElegida = citas.get(i);
            System.out.println((i + 1) + ". Cita de: " 
                + citaElegida.getAnimal().getOwner().getName()
                + ", Mascota: " + citaElegida.getAnimal().getName()
                + ", Fecha: " + citaElegida.getFecha() + " Estado: " + citaElegida.getEstado().name());
        }

        Cita citaSeleccionada = null;
        while (true) {
            try {
                System.out.print("Ingrese el número de la cita que desea editar: ");
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= 1 && opcion <= citas.size()) {
                    citaSeleccionada = citas.get(opcion - 1);
                    break;
                } else {
                    System.out.println("-------------------------------------");
                    System.out.println("  Opción no válida. Por favor, seleccione un número dentro del rango.");
                    System.out.println("-------------------------------------");
                }
            } catch (NumberFormatException e) {
                System.out.println("-------------------------------------");
                System.out.println("  Entrada inválida. Por favor, ingrese un número válido.");
                System.out.println("-------------------------------------");
            }
        }

        System.out.println("-------------------------------------");
        System.out.println(" Estado actual de la cita: " + citaSeleccionada.getEstado());
        System.out.println("-------------------------------------");
        System.out.println("Seleccione el nuevo estado para la cita:");
        EstadoCita[] estados = EstadoCita.values();
        for (int i = 0; i < estados.length; i++) {
            System.out.println((i + 1) + ". " + estados[i]);
        }

        EstadoCita nuevoEstado = null;
        while (true) {
            try {
                System.out.print("Ingrese el número correspondiente al nuevo estado: ");
                int estadoOpcion = Integer.parseInt(scanner.nextLine());
                if (estadoOpcion >= 1 && estadoOpcion <= estados.length) {
                    nuevoEstado = estados[estadoOpcion - 1];
                    break;
                } else {
                    System.out.println("-------------------------------------");
                    System.out.println("  Opción no válida. Intente de nuevo.");
                    System.out.println("-------------------------------------");
                }
            } catch (NumberFormatException e) {
                System.out.println("-------------------------------------");
                System.out.println("  Entrada inválida. Por favor, ingrese un número válido.");
                System.out.println("-------------------------------------");
            }
        }

        citaSeleccionada.setEstado(nuevoEstado);
        boolean exito = DateController.actualizarCita(citaSeleccionada);

        if (exito) {
            System.out.println("-------------------------------------");
            System.out.println(" Estado de la cita actualizado con éxito.");
            System.out.println("-------------------------------------");
        } else {
            System.out.println("-------------------------------------");
            System.out.println("  Hubo un error al actualizar el estado de la cita. Intente de nuevo.");
            System.out.println("-------------------------------------");
        }
    }
    
    public void verTodasLasCitas() {
    ArrayList<Cita> citas = DateController.obtenerCitas();

    if (citas.isEmpty()) {
        System.out.println("No hay citas registradas.");
        return;
    }

    System.out.println("Listado de todas las citas:");
    System.out.println("----------------------------------------------------------");

    for (Cita cita : citas) {
        System.out.println("ID Cita: " + cita.getId());
        System.out.println("Fecha: " + cita.getFecha());
        System.out.println("Hora: " + cita.getHora());
        System.out.println("Estado: " + cita.getEstado());

        System.out.println("Mascota:");
        System.out.println("  - ID: " + cita.getAnimal().getId());
        System.out.println("  - Nombre: " + cita.getAnimal().getName());
        System.out.println("  - Especie: " + cita.getAnimal().getSpecie());
        System.out.println("  - Dueño: " + cita.getAnimal().getOwner().getName());

        System.out.println("----------------------------------------------------------");
    }
}
    
}
