package com.mycompany.petsociety.view;

import com.mycompany.petsociety.controllers.JornadasController;
import com.mycompany.petsociety.controllers.VacunasController;
import com.mycompany.petsociety.controllers.VeterinarianController;
import com.mycompany.petsociety.models.Admin;
import com.mycompany.petsociety.models.JornadasEspeciales;
import com.mycompany.petsociety.models.TipoAnimalInsumo;
import com.mycompany.petsociety.models.Vacuna;
import com.mycompany.petsociety.models.Veterinarian;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminJornadasUI {
    private Scanner scanner;
    
    public AdminJornadasUI(Admin admin){
        this.scanner = new Scanner(System.in);
    }
    
    public void menuJornadas() {
    OUTER:
    while (true) {
        System.out.println("=========================================");
        System.out.println("         Selección de Jornadas          ");
        System.out.println("=========================================");
        
        System.out.println("\n¿Qué tipo de jornada deseas llevar a cabo?");
        System.out.println("1. Vacunación");
        System.out.println("2. Esterilización");
        System.out.println("0. Volver");

        System.out.print("Elige una opción (0-2): ");
        int opcion = obtenerEntero();

        if (opcion == -1) return;

        switch (opcion) {
            case 0:
                System.out.println("\nVolviendo al menú anterior...");
                break OUTER;  
            case 1:
                jornadaVacunacion(); 
                break;
            case 2:
                jornadaEsterilizacion();
                break;
            default:
                System.out.println("\n¡Opción no válida! Por favor, elige una opción válida.");
                break;
        }
    }
}
    
    public void jornadaEsterilizacion() {
        String nombreJornada = "Esterilización";
        LocalDate fecha = LocalDate.now();

        System.out.println("=========================================");
        System.out.println("        Ingreso de Jornada de Esterilización");
        System.out.println("=========================================");

        System.out.println("Ingrese una corta descripción de la jornada que se llevará a cabo: ");
        String descripcion = scanner.nextLine();

        System.out.println("\n¿Qué especie se va a esterilizar en esta jornada?");
        System.out.println("1. Caninos");
        System.out.println("2. Felinos");
        System.out.println("3. Roedores");
        System.out.println("4. Aves");

        int opcionEspecie = obtenerEntero();
        if (opcionEspecie == -1) return;  // Si la entrada es inválida, salir

        TipoAnimalInsumo especieSeleccionada = null;
        switch (opcionEspecie) {
            case 1:
                especieSeleccionada = TipoAnimalInsumo.Canino;
                break;
            case 2:
                especieSeleccionada = TipoAnimalInsumo.Felino;
                break;
            case 3:
                especieSeleccionada = TipoAnimalInsumo.Roedor;
                break;
            case 4:
                especieSeleccionada = TipoAnimalInsumo.Ave;
                break;
            default:
                System.out.println("¡Opción no válida! Por favor, elige una opción entre 1 y 4.");
                return;
        }

        System.out.println("\nIngrese la demanda esperada para la jornada (número de animales): ");
        int demandaEsperada = obtenerEntero();
        if (demandaEsperada == -1) return;

        int maxVeterinarios;
        if (demandaEsperada > 1 && demandaEsperada < 50) {
            maxVeterinarios = 2;
        } else if (demandaEsperada >= 50) {
            maxVeterinarios = 4;
        } else {
            System.out.println("La demanda debe ser mayor a 1. Operación cancelada.");
            return;
        }

        ArrayList<Veterinarian> listaVeterinarios = VeterinarianController.obtenerTodosLosVeterinarios();
        if (listaVeterinarios.isEmpty()) {
            System.out.println("No hay veterinarios disponibles en este momento. Operación cancelada.");
            return;
        }

        System.out.println("\nSeleccione hasta " + maxVeterinarios + " veterinarios para el equipo:");
        for (int i = 0; i < listaVeterinarios.size(); i++) {
            System.out.println((i + 1) + ". " + listaVeterinarios.get(i).getNombre());
        }

        System.out.println("\nIngrese los números de los veterinarios seleccionados, separados por comas (por ejemplo: 1,3):");
        String[] indicesSeleccionados = scanner.nextLine().split(",");
        ArrayList<Veterinarian> equipoVeterinario = new ArrayList<>();

        for (String indice : indicesSeleccionados) {
            try {
                int seleccionado = Integer.parseInt(indice.trim()) - 1;
                if (seleccionado >= 0 && seleccionado < listaVeterinarios.size()) {
                    if (equipoVeterinario.size() < maxVeterinarios) {
                        equipoVeterinario.add(listaVeterinarios.get(seleccionado));
                    } else {
                        System.out.println("Ya se seleccionaron los " + maxVeterinarios + " veterinarios permitidos.");
                        break;
                    }
                } else {
                    System.out.println("Índice " + (seleccionado + 1) + " fuera de rango. Ignorando.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: " + indice + ". Ignorando.");
            }
        }

        System.out.println("\nEquipo Veterinario Seleccionado:");
        for (Veterinarian v : equipoVeterinario) {
            System.out.println("- " + v.getNombre());
        }

        if (equipoVeterinario.size() < maxVeterinarios) {
            System.out.println("\nAdvertencia: No se seleccionaron todos los veterinarios necesarios para la jornada.");
        }

        System.out.println("\n¿Cuántos " + especieSeleccionada.name() + " se van a esterilizar?");
        int impactoJornada = obtenerEntero();
        if (impactoJornada == -1) return;

        JornadasEspeciales jornada = new JornadasEspeciales(0, nombreJornada, fecha, descripcion, especieSeleccionada, demandaEsperada, impactoJornada);
        JornadasController.insertarJornadasRealizadas(jornada);

        System.out.println("\n¡Jornada de Esterilización concluida con éxito!");
    }

    public void jornadaVacunacion() {
    String nombreJornada = "Vacunación";
    LocalDate fecha = LocalDate.now();

    System.out.println("=========================================");
    System.out.println("        Ingreso de Jornada de Vacunación     ");
    System.out.println("=========================================");
    
    System.out.println("Ingrese una breve descripción de la jornada que se llevará a cabo: ");
    String descripcion = scanner.nextLine();

    System.out.println("\n¿Qué especie se va a vacunar en esta jornada?");
    System.out.println("1. Caninos");
    System.out.println("2. Felinos");
    System.out.println("3. Roedores");
    System.out.println("4. Aves");

    int opcionEspecie = obtenerEntero();
    if (opcionEspecie == -1) return;

    TipoAnimalInsumo especieSeleccionada = null;
    switch (opcionEspecie) {
        case 1:
            especieSeleccionada = TipoAnimalInsumo.Canino;
            break;
        case 2:
            especieSeleccionada = TipoAnimalInsumo.Felino;
            break;
        case 3:
            especieSeleccionada = TipoAnimalInsumo.Roedor;
            break;
        case 4:
            especieSeleccionada = TipoAnimalInsumo.Ave;
            break;
        default:
            System.out.println("¡Opción no válida! Por favor, elige una opción entre 1 y 4.");
            return;
    }

    System.out.println("\nIngrese la demanda esperada para la jornada (número de animales): ");
    int demandaEsperada = obtenerEntero();
    if (demandaEsperada == -1) return;

    int maxVeterinarios;
    if (demandaEsperada > 1 && demandaEsperada < 50) {
        maxVeterinarios = 2;
    } else if (demandaEsperada >= 50) {
        maxVeterinarios = 4;
    } else {
        System.out.println("La demanda debe ser mayor a 1. Operación cancelada.");
        return;
    }

    ArrayList<Veterinarian> listaVeterinarios = VeterinarianController.obtenerTodosLosVeterinarios();
    if (listaVeterinarios.isEmpty()) {
        System.out.println("No hay veterinarios disponibles en este momento. Operación cancelada.");
        return;
    }

    System.out.println("\nSeleccione hasta " + maxVeterinarios + " veterinarios para el equipo:");
    for (int i = 0; i < listaVeterinarios.size(); i++) {
        System.out.println((i + 1) + ". " + listaVeterinarios.get(i).getNombre());
    }

    System.out.println("\nIngrese los números de los veterinarios seleccionados, separados por comas (por ejemplo: 1,3):");
    String[] indicesSeleccionados = scanner.nextLine().split(",");
    ArrayList<Veterinarian> equipoVeterinario = new ArrayList<>();

    for (String indice : indicesSeleccionados) {
        try {
            int seleccionado = Integer.parseInt(indice.trim()) - 1;
            if (seleccionado >= 0 && seleccionado < listaVeterinarios.size()) {
                if (equipoVeterinario.size() < maxVeterinarios) {
                    equipoVeterinario.add(listaVeterinarios.get(seleccionado));
                } else {
                    System.out.println("Ya se seleccionaron los " + maxVeterinarios + " veterinarios permitidos.");
                    break;
                }
            } else {
                System.out.println("Índice " + (seleccionado + 1) + " fuera de rango. Ignorando.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida: " + indice + ". Ignorando.");
        }
    }

    System.out.println("\nEquipo Veterinario Seleccionado:");
    for (Veterinarian v : equipoVeterinario) {
        System.out.println("- " + v.getNombre());
    }

    if (equipoVeterinario.size() < maxVeterinarios) {
        System.out.println("\nAdvertencia: No se seleccionaron todos los veterinarios necesarios para la jornada.");
    }

    int impactoJornada = 0;
    while (true) {
        System.out.println("\n¿Cuántos " + especieSeleccionada.name() + " se van a vacunar?");
        int cantidadAnimales = obtenerEntero();
        if (cantidadAnimales == -1) return;

        if (cantidadAnimales < 0 || cantidadAnimales > demandaEsperada) {
            System.out.println("Cantidad no válida. Debe estar entre 0 y " + demandaEsperada);
            continue;
        }

        ArrayList<Vacuna> listaVacunas = VacunasController.mostrarTodasLasVacunasPorJornadaYEspecie(especieSeleccionada);

        if (listaVacunas.isEmpty()) {
            System.out.println("No hay vacunas disponibles para esta especie.");
            return;
        }

        int totalVacunasAsignadas = 0;
        
        while (totalVacunasAsignadas < cantidadAnimales) {
            System.out.println("\nVacunas disponibles para " + especieSeleccionada.name() + ":");
            for (int i = 0; i < listaVacunas.size(); i++) {
                System.out.println((i + 1) + ". " + listaVacunas.get(i).getNombre());
            }

            System.out.println("\nSeleccione una vacuna (ingrese el número): ");
            int opcionVacuna = obtenerEntero();
            if (opcionVacuna == -1) return;

            if (opcionVacuna < 0 || opcionVacuna >= listaVacunas.size()) {
                System.out.println("Opción no válida. Intente de nuevo.");
                continue;
            }

            Vacuna vacunaSeleccionada = listaVacunas.get(opcionVacuna);

            System.out.println("¿Cuántas dosis de la vacuna " + vacunaSeleccionada.getNombre() + " desea usar?");
            int cantidadVacunas = obtenerEntero();
            if (cantidadVacunas == -1) return;

            impactoJornada = cantidadAnimales;
            if (cantidadVacunas < 0) {
                System.out.println("Cantidad no válida. Debe ser un número positivo.");
            } else if (totalVacunasAsignadas + cantidadVacunas > cantidadAnimales) {
                System.out.println("La cantidad excede el número de animales a vacunar (" + cantidadAnimales + ").");
            } else {
                totalVacunasAsignadas += cantidadVacunas;
                System.out.println("Se asignaron " + cantidadVacunas + " dosis de " + vacunaSeleccionada.getNombre());
                VacunasController.restarCantidadInsumos(vacunaSeleccionada.getId(), cantidadVacunas);
            }
        }
        
        System.out.println("\n¡Vacunación configurada con éxito!");
        break;
    }

    JornadasEspeciales jornada = new JornadasEspeciales(0, nombreJornada, fecha, descripcion, especieSeleccionada, demandaEsperada, impactoJornada);
    JornadasController.insertarJornadasRealizadas(jornada);

    System.out.println("\n¡Jornada de Vacunación concluida con éxito!");
}

    private int obtenerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Intente de nuevo.");
            return -1;
        }
    }
}
