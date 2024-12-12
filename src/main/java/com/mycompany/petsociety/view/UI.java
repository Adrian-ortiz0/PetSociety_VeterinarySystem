package com.mycompany.petsociety.view;

import com.mycompany.petsociety.controllers.AdminController;
import com.mycompany.petsociety.controllers.OwnerController;
import com.mycompany.petsociety.models.Admin;
import com.mycompany.petsociety.models.Owner;

import java.util.Scanner;

public class UI {
    private Scanner scanner;
    
    public UI(){
        this.scanner = new Scanner(System.in);
    }
    
    public void start(){
        OUTER:
        while (true) {
            try {
                System.out.println("      ==    WELCOME TO PET SOCIETY    ==");
                System.out.println();
                System.out.println("=================================================");
                System.out.println();
                System.out.println("1. Inicio sesion usuario");
                System.out.println("2. Inicio sesion admin");
                System.out.println("0. Salir");
                System.out.println();
                System.out.println("=================================================");
                int input = Integer.parseInt(scanner.nextLine());
                switch (input) {
                    case 0:
                        System.out.println("Adios...");
                        break OUTER;
                    case 1:
                        System.out.println("Type your email: ");
                        String correoUsuario = scanner.nextLine();
                        System.out.println("Type your cedula: ");
                        String cedula = scanner.nextLine();
                        try {
                            Owner owner = OwnerController.iniciarSesion(correoUsuario, cedula);
                            if (owner != null) {
                                System.out.println("Login successful!");
                                OwnersUI owu = new OwnersUI(owner);
                                owu.menuUsuario(owner);
                            } else {
                                System.out.println("Invalid email or cedula. Please try again.");
                            }
                        } catch (Exception e) {
                            System.out.println("An error occurred during user login: " + e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.println("Type your email: ");
                        String correoAdministrador = scanner.nextLine();
                        System.out.println("Type your cedula: ");
                        String cedulaAdmin = scanner.nextLine();
                        try {
                            Admin admin = AdminController.iniciarSesionAdmin(correoAdministrador, cedulaAdmin);
                            if(admin != null){
                                System.out.println("Login successful!");
                                menuAdmin(admin);
                            } else {
                                System.out.println("Invalid email or cedula. Please try again.");
                            }
                        } catch (Exception e) {
                            System.out.println("An error occurred during admin login: " + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
    
    public void menuAdmin(Admin admin){
        System.out.println("Bienvenido admin: " + admin.getName());
        System.out.println("====================================");
        OUTER:
        while (true) {
            try {
                System.out.println("Que deseas hacer hoy?");
                
                System.out.println("--------------------------------------------");
                
                System.out.println("1. Gestion Propietarios");
                System.out.println("2. Gestion Adopcion");
                System.out.println("3. Gestion Citas");
                System.out.println("4. Gestion Inventario");
                System.out.println("5. Gestion Jornadas");
                System.out.println("6. Gestion Veterinarios");
                System.out.println("7. Gestion Reportes");
                System.out.println("8. Gestion Club");
                System.out.println("0. Salir");
                
                System.out.println("--------------------------------------------");
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 0:
                        System.out.println("Saliendo...");
                        break OUTER;
                    case 1:
                        AdminOwnersUI adminOwnersUI = new AdminOwnersUI(admin);
                        adminOwnersUI.menuGestionPropietarios(admin);
                        break;
                    case 2:
                        AdoptionUI adopcionUI = new AdoptionUI(admin);
                        adopcionUI.menuGestionAdopciones(admin);
                        break;
                    case 3:
                        AdminCitasUI adminCitasUi = new AdminCitasUI(admin);
                        adminCitasUi.menuCitasAdmin(admin);
                        break;
                    case 4:
                        AdminInventaryUI adminInventaryUI = new AdminInventaryUI(admin);
                        adminInventaryUI.menuGestionInventario(admin);
                        break;
                    case 5:
                        AdminJornadasUI adminJornadasUI = new AdminJornadasUI(admin);
                        adminJornadasUI.menuJornadas();
                        break;
                    case 6:
                        AdminVetsUI adminVetsUI = new AdminVetsUI();
                        adminVetsUI.menuGestionVeterinarios(admin);
                        break;
                    case 7:
                        AdminReportesUI adminReportesUI = new AdminReportesUI(admin);
                        adminReportesUI.menuReportes();
                        break;
                    case 8:
                        AdminClubUI adminClubUI = new AdminClubUI();
                        adminClubUI.menuClub();
                        break;
                    default:
                        System.out.println("No existe esa opción. Por favor intenta de nuevo.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred while navigating the admin menu: " + e.getMessage());
            }
        }
    }
}