package com.mycompany.petsociety.view;

import com.mycompany.petsociety.controllers.AdminController;
import com.mycompany.petsociety.controllers.OwnerController;
import com.mycompany.petsociety.models.Admin;
import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.Gato;
import com.mycompany.petsociety.models.Hamster;
import com.mycompany.petsociety.models.Loro;
import com.mycompany.petsociety.models.Owner;
import com.mycompany.petsociety.models.Perro;
import com.mycompany.petsociety.models.PetStatus;
import com.mycompany.petsociety.models.Sex;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AdoptionUI {
    private Scanner scanner;
    
    public AdoptionUI(Admin admin){
        this.scanner = new Scanner(System.in);
    }
    
    public void menuGestionAdopciones(Admin admin) {
    OUTER:
    while (true) {
        try {
            System.out.println("==============================================");
            System.out.println("       Menú de Gestión de Adopciones");
            System.out.println("==============================================");
            System.out.println("¿Qué deseas hacer?");
            System.out.println(" 1. Registrar animales para adopción");
            System.out.println(" 0. Salir");
            System.out.println("----------------------------------------------");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.println("\nIniciando registro de animales para adopción...");
                    registrarMascotasParaAdoptar();
                    break;
                case 0:
                    System.out.println("\nVolviendo al menú anterior...");
                    break OUTER;
                default:
                    System.out.println("\nOpción no válida. Intenta nuevamente.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("\nError al procesar la opción. Intenta nuevamente.");
        }
    }
}
    
    public void registrarMascotasParaAdoptar() {
    OUTER:
    while (true) {
        try {
            System.out.println("==============================================");
            System.out.println("         Registro de Mascotas para Adopción");
            System.out.println("==============================================");
            System.out.println("¿Qué deseas hacer?");
            System.out.println(" 1. Mascota existente");
            System.out.println(" 2. Nuevo animal");
            System.out.println(" 0. Salir");
            System.out.println("----------------------------------------------");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 0:
                    System.out.println("\nVolviendo al menú anterior...");
                    break OUTER;
                case 1:
                    System.out.println("\nIniciando proceso para dar mascota existente en adopción...");
                    darMascotaEnAdopcion();
                    break;
                case 2:
                    System.out.println("\nIniciando proceso para registrar nuevo animal...");
                    mascotaRescatada();
                    break;
                default:
                    System.out.println("\nOpción no válida. Intenta nuevamente.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("\nError al procesar la opción. Intenta nuevamente.");
        }
    }
}
    
    public void mascotaRescatada() {
        try {
            System.out.println("==============================================");
            System.out.println("     Registro de Mascota Rescatada");
            System.out.println("==============================================");

            System.out.println("¿Qué tipo de mascota rescataste?");
            System.out.println(" 1. Loro");
            System.out.println(" 2. Perro");
            System.out.println(" 3. Gato");
            System.out.println(" 4. Hamster");
            System.out.println("Elige una opción (1-4):");
            int tipoMascota = Integer.parseInt(scanner.nextLine());

            System.out.println("\nIngresa el nombre de la mascota:");
            String name = scanner.nextLine();

            System.out.println("\nIngresa la raza de la mascota:");
            String breed = scanner.nextLine();

            System.out.println("\nIngresa la fecha de nacimiento aproximada (yyyy-MM-dd):");
            LocalDate birthDate = LocalDate.parse(scanner.nextLine());

            System.out.println("\nSelecciona el sexo de la mascota:");
            for (Sex s : Sex.values()) {
                System.out.println(s.getIdSex() + ". " + s.getSex());
            }
            int sexChoice = Integer.parseInt(scanner.nextLine());
            Sex sex = Sex.values()[sexChoice - 1];

            System.out.println("\nIngresa la URL de la foto de la mascota (opcional):");
            String photo = scanner.nextLine();

            Animal newAnimal = null;

            switch (tipoMascota) {
                case 1:
                    newAnimal = new Loro(name, breed, birthDate, sex, "0", photo.isEmpty() ? null : photo, PetStatus.ESPERANDO_ADOPCION, null);
                    break;
                case 2:
                    newAnimal = new Perro(name, breed, birthDate, sex, "0", photo.isEmpty() ? null : photo, PetStatus.ESPERANDO_ADOPCION, null);
                    break;
                case 3:
                    newAnimal = new Gato(name, breed, birthDate, sex, "0", photo.isEmpty() ? null : photo, PetStatus.ESPERANDO_ADOPCION, null);
                    break;
                case 4:
                    newAnimal = new Hamster(name, breed, birthDate, sex, "0", photo.isEmpty() ? null : photo, PetStatus.ESPERANDO_ADOPCION, null);
                    break;
                default:
                    System.out.println("\nOpción no válida. Intenta nuevamente.");
                    return;
            }

            boolean petInserted = AdminController.insertarAnimalesParaAdopcion(newAnimal);
            if (petInserted) {
                System.out.println("\nMascota registrada correctamente: " + name);
            } else {
                System.out.println("\nHubo un problema al registrar la mascota.");
            }

        } catch (Exception e) {
            System.out.println("\nError al registrar la mascota. Intenta nuevamente.");
        }
    }
    
    public void darMascotaEnAdopcion() {
        try {
            System.out.println("==============================================");
            System.out.println("     Dar Mascota en Adopción");
            System.out.println("==============================================");

            System.out.println("Ingresa la cédula del propietario: ");
            String cedula = scanner.nextLine();
            Owner ownerElegido = OwnerController.obtenerPropietarioPorCedula(cedula);

            if (ownerElegido == null) {
                System.out.println("\nNo se encontró un propietario con esa cédula.");
                return;
            }

            ArrayList<Animal> listaMascotasOwner = OwnerController.obtenerMascotasDePropietario(ownerElegido);

            if (listaMascotasOwner.isEmpty()) {
                System.out.println("\nEl propietario no tiene mascotas registradas.");
                return;
            }

            System.out.println("\nSelecciona la mascota que deseas dar en adopción:");
            for (int i = 0; i < listaMascotasOwner.size(); i++) {
                Animal mascota = listaMascotasOwner.get(i);
                System.out.println((i + 1) + ". " + mascota.getName() + " (" + mascota.getSpecie() + ")");
            }

            System.out.println("\nIngresa el número de la mascota:");
            int seleccion;
            try {
                seleccion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Por favor, ingresa un número.");
                return;
            }

            if (seleccion < 1 || seleccion > listaMascotasOwner.size()) {
                System.out.println("\nSelección inválida. Por favor, intenta de nuevo.");
                return;
            }

            Animal mascotaSeleccionada = listaMascotasOwner.get(seleccion - 1);
            System.out.println("\nHas seleccionado a: " + mascotaSeleccionada.getName());

            System.out.println("\n¿Estás seguro de que quieres dar en adopción a esta mascota? (1 para sí / 2 para no)");
            int confirmacion;
            try {
                confirmacion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Operación cancelada.");
                return;
            }

            switch (confirmacion) {
                case 1:
                    boolean resultado = OwnerController.darMascotaEnAdopcion(mascotaSeleccionada);
                    if (resultado) {
                        System.out.println("\nLa mascota ha sido dada en adopción exitosamente.");
                    } else {
                        System.out.println("\nOcurrió un problema al intentar dar en adopción a la mascota.");
                    }
                    break;
                case 2:
                    System.out.println("\nOperación cancelada.");
                    break;
                default:
                    System.out.println("\nOpción no válida. Operación cancelada.");
                    break;
            }

        } catch (Exception e) {
            System.out.println("\nError al dar en adopción la mascota. Intenta nuevamente.");
        }
    }
}