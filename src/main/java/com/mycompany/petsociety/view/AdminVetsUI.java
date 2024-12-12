
package com.mycompany.petsociety.view;

import com.mycompany.petsociety.controllers.AdminController;
import com.mycompany.petsociety.models.Admin;
import com.mycompany.petsociety.models.Veterinarian;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminVetsUI {
    
    private Scanner scanner;
    
    public AdminVetsUI(){
        this.scanner = new Scanner(System.in);
    }
    
    public void menuGestionVeterinarios(Admin admin) {
    while (true) {
        try {
            System.out.println("==============================================");
            System.out.println("     Gestión de Veterinarios");
            System.out.println("==============================================");
            
            System.out.println("¿Qué deseas hacer?");
            System.out.println();
            System.out.println(" 1. Contratar veterinarios");
            System.out.println(" 2. Consultar veterinarios");
            System.out.println(" 3. Despedir veterinarios");
            System.out.println(" 0. Salir");
            System.out.println("----------------------------------------------");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 0:
                    System.out.println("\nVolviendo al menú anterior...");
                    return;
                case 1:
                    System.out.println("\nIniciando proceso de contratación de veterinarios...");
                    contratarVeterinarios(admin);
                    break;
                case 2:
                    System.out.println("\nConsultando veterinarios...");
                    mostrarTodosVeterinarios(admin);
                    break;
                case 3:
                    System.out.println("\nIniciando proceso de despido de veterinarios...");
                    despedirVeterinario(admin);
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor, selecciona una opción correcta.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("\nError al procesar la opción. Intenta nuevamente.");
        }
    }
}
    
    public void despedirVeterinario(Admin admin) {
    try {
        System.out.println("==============================================");
        System.out.println("     Despedir Veterinario");
        System.out.println("==============================================");

        System.out.println("\nIngresa la cédula del veterinario a despedir:");
        String cedula = scanner.nextLine();

        System.out.println("\nEstás seguro de que deseas despedir al veterinario con cédula: " + cedula + "?");
        System.out.println("1. Sí");
        System.out.println("2. No");
        int confirmacion = Integer.parseInt(scanner.nextLine());

        if (confirmacion == 1) {
            boolean resultado = AdminController.despedirVeterinarios(cedula);
            if (resultado) {
                System.out.println("\nVeterinario con cédula " + cedula + " despedido exitosamente.");
            } else {
                System.out.println("\nHubo un problema al intentar despedir al veterinario. Verifica la cédula.");
            }
        } else {
            System.out.println("\nOperación cancelada. El veterinario no ha sido despedido.");
        }

    } catch (Exception e) {
        System.out.println("\nError al procesar la solicitud. Intenta nuevamente.");
    }
}
    
    public void mostrarTodosVeterinarios(Admin admin) {
    try {
        System.out.println("==============================================");
        System.out.println("           Lista de Veterinarios");
        System.out.println("==============================================");

        System.out.println("\nVETERINARIOS REGISTRADOS:");
        System.out.println("---------------------------");
        
        ArrayList<String> veterinarios = AdminController.mostrarVeterinarios();
        if (veterinarios.isEmpty()) {
            System.out.println("No se han encontrado veterinarios registrados.");
        } else {
            for (String vet : veterinarios) {
                System.out.println(vet);
            }
        }
        System.out.println("----------------------------------------------");

    } catch (Exception e) {
        System.out.println("\nError al mostrar los veterinarios. Intenta nuevamente.");
    }
}
    
    public void contratarVeterinarios(Admin admin) {
        try {
            System.out.println("==============================================");
            System.out.println("     Contratar Veterinario");
            System.out.println("==============================================");

            System.out.println("\nPor favor, ingresa la siguiente información del veterinario:");

            System.out.print("Cédula del veterinario: ");
            String cedula = scanner.nextLine();

            System.out.print("Nombre del veterinario: ");
            String nombre = scanner.nextLine();

            System.out.print("Licencia del veterinario: ");
            String licencia = scanner.nextLine();

            System.out.print("Especialidad (opcional): ");
            String especialidad = scanner.nextLine();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            Veterinarian vet = new Veterinarian();
            vet.setCedula(cedula);
            vet.setNombre(nombre);
            vet.setLicencia(licencia);
            vet.setEspecialidad(especialidad);
            vet.setTelefono(telefono);
            vet.setEmail(email);

            boolean resultado = AdminController.insertarVeterinarios(vet);
            if (resultado) {
                System.out.println("\nVeterinario contratado exitosamente.");
            } else {
                System.out.println("\nHubo un problema al contratar al veterinario. Intenta nuevamente.");
            }

        } catch (Exception e) {
            System.out.println("\nError al contratar el veterinario. Intenta nuevamente.");
        }
    }
}