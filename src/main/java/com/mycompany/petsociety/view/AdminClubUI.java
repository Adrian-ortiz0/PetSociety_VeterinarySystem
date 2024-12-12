package com.mycompany.petsociety.view;

import com.mycompany.petsociety.controllers.ClubController;
import com.mycompany.petsociety.controllers.OwnerController;
import com.mycompany.petsociety.models.Owner;
import java.util.Scanner;

public class AdminClubUI {
    private Scanner scanner;
    
    public AdminClubUI() {
        this.scanner = new Scanner(System.in);
    }
    
    public void menuClub() {
        OUTER:
        while (true) {
            System.out.println("=====================================");
            System.out.println("           MENÚ DEL CLUB             ");
            System.out.println("=====================================");
            System.out.println("¿Qué deseas hacer?");
            System.out.println("  1. Registrar miembros al club");
            System.out.println("  0. Salir");
            System.out.println("=====================================");
            System.out.print("Selecciona una opción: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 0:
                        System.out.println("-------------------------------------");
                        System.out.println("            Volviendo...             ");
                        System.out.println("-------------------------------------");
                        break OUTER;
                    case 1:
                        registrarMiembros();
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

    public void registrarMiembros() {
        System.out.println("=====================================");
        System.out.println("    REGISTRO DE MIEMBROS DEL CLUB    ");
        System.out.println("=====================================");
        System.out.print("Ingresa la cédula del owner: ");
        String cedula = scanner.nextLine();
        try {
            Owner owner = OwnerController.obtenerPropietarioPorCedula(cedula);
            if (owner != null) {
                ClubController.insertarMiembrosEnClub(owner);
                System.out.println("-------------------------------------");
                System.out.println("    Miembro registrado con éxito!    ");
                System.out.println("-------------------------------------");
            } else {
                System.out.println("-------------------------------------");
                System.out.println(" No se encontró un propietario con esa cédula.");
                System.out.println("-------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("-------------------------------------");
            System.out.println(" Hubo un error al registrar al miembro. Intenta de nuevo.");
            System.out.println("-------------------------------------");
        }
    }
}