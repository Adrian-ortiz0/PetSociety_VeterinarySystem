package com.mycompany.petsociety.view;

import com.mycompany.petsociety.controllers.AdminController;
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
import java.util.Scanner;

public class AdminOwnersUI {
    private Scanner scanner;

    public AdminOwnersUI(Admin admin) {
        this.scanner = new Scanner(System.in);
    }
    
    public void menuGestionPropietarios(Admin admin) {
    OUTER:
    while (true) {
        try {
            System.out.println("==============================================");
            System.out.println("       Gestión de Propietarios");
            System.out.println("==============================================");

            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1. Registrar propietarios");
            System.out.println("2. Editar información de propietarios");
            System.out.println("3. Eliminar propietarios");
            System.out.println("4. Mostrar todos los propietarios y sus mascotas");
            System.out.println("0. Volver");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 0:
                    System.out.println("\nVolviendo...");
                    break OUTER;
                case 1:
                    registrarPropietario();
                    break;
                case 2:
                    menuEdiciones();
                    break;
                case 3:
                    menuEliminaciones();
                    break;
                case 4:
                    mostrarPropietariosConMascotas();
                    break;
                default:
                    System.out.println("\nOpción no válida. Intenta nuevamente.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("\nError en el procesamiento. Intenta nuevamente.");
        }
    }
}
    
    public void registrarPropietario() {
        try {
            System.out.println("==============================================");
            System.out.println("       Registro de Propietario");
            System.out.println("==============================================");

            System.out.println("\nPor favor, ingresa la siguiente información del propietario:");

            System.out.print("Nombre del propietario: ");
            String nombre = scanner.nextLine();

            System.out.print("Cédula del propietario: ");
            String cedula = scanner.nextLine();

            System.out.print("Dirección del propietario: ");
            String direccion = scanner.nextLine();

            System.out.print("Teléfono del propietario: ");
            String telefono = scanner.nextLine();

            System.out.print("Email del propietario: ");
            String email = scanner.nextLine();

            System.out.print("Contacto de emergencia: ");
            String contactoEmergencia = scanner.nextLine();

            Owner owner = new Owner(nombre, cedula, direccion, telefono, email, contactoEmergencia);

            boolean resultado = AdminController.insertarOwner(owner);
            if (resultado) {
                System.out.println("\nPropietario registrado exitosamente.");
            } else {
                System.out.println("\nHubo un problema al registrar al propietario. Intenta nuevamente.");
            }

            System.out.println("\n¿Desea registrar alguna mascota? (1. Sí / 2. No)");
            int opcionRegistro = Integer.parseInt(scanner.nextLine());
            if (opcionRegistro == 1) {
                RegistrarMascota(owner);
            } else if (opcionRegistro != 2) {
                System.out.println("Opción no válida. Registro de mascota cancelado.");
            }

        } catch (Exception e) {
            System.out.println("\nError al registrar propietario. Intenta nuevamente.");
        }
    }
    
    public void menuEliminaciones() {
        try {
            System.out.println("==============================================");
            System.out.println("           Eliminación de Propietario");
            System.out.println("==============================================");

            System.out.print("\nPor favor, ingresa la cédula del propietario a eliminar: ");
            String cedula = scanner.nextLine();

            boolean eliminado = AdminController.eliminarPropietarioPorCedula(cedula);

            if (eliminado) {
                System.out.println("\nEl propietario con cédula " + cedula + " ha sido eliminado exitosamente.");
            } else {
                System.out.println("\nNo se encontró un propietario con esa cédula. Verifica e intenta nuevamente.");
            }

        } catch (Exception e) {
            System.out.println("\nError al eliminar propietario. Intenta nuevamente.");
        }
    }
    
    public void menuEdiciones() {
    try {
        System.out.println("==============================================");
        System.out.println("         Edición de Información del Propietario ");
        System.out.println("==============================================");

        System.out.print("\nPor favor, ingresa la cédula del propietario: ");
        String cedula = scanner.nextLine();

        Owner owner = AdminController.obtenerOwnerPorCedula(cedula);

        if (owner == null) {
            System.out.println("\nNo se encontró un propietario con esa cédula.");
            return;
        }

        OUTER:
        while (true) {
            System.out.println("\n¿Qué información deseas editar?");
            System.out.println("=======================================");
            System.out.println("1. Editar dirección");
            System.out.println("2. Editar email");
            System.out.println("3. Editar teléfono");
            System.out.println("4. Añadir nueva mascota");
            System.out.println("0. Volver");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 0:
                    System.out.println("Volviendo al menú anterior...");
                    break OUTER;
                case 1:
                    editarDireccion(owner);
                    break;
                case 2:
                    editarEmail(owner);
                    break;
                case 3:
                    editarTelefono(owner);
                    break;
                case 4:
                    RegistrarMascota(owner);
                    break;
                default:
                    System.out.println("\n¡Opción no válida! Por favor, ingresa una opción válida.");
                    break;
            }
        }
    } catch (Exception e) {
        System.out.println("\nError al editar la información del propietario. Intenta nuevamente.");
    }
}
    
    public void editarDireccion(Owner owner){
        try {
            System.out.println("Escribe la nueva direccion: ");
            String nuevaDireccion = scanner.nextLine();
            AdminController.actualizarDireccionOwner(owner, nuevaDireccion);
        } catch (Exception e) {
            System.out.println("Error al editar direccion. Intenta nuevamente.");
        }
    }
    
    public void editarEmail(Owner owner){
        try {
            System.out.println("Esribe el nuevo correo: ");
            String nuevoCorreo = scanner.nextLine();
            AdminController.actualizarEmailOwner(owner, nuevoCorreo);
        } catch (Exception e) {
            System.out.println("Error al editar email. Intenta nuevamente.");
        }
    }
    
    public void editarTelefono(Owner owner){
        try {
            System.out.println("Escriba el nuevo telefono: ");
            String nuevoTelefono = scanner.nextLine();
            AdminController.actualizarTelefonoOwner(owner, nuevoTelefono);
        } catch (Exception e) {
            System.out.println("Error al editar telefono. Intenta nuevamente.");
        }
    }
    
    public void mostrarPropietariosConMascotas(){
        try {
            for (String owner : AdminController.obtenerPropietariosConMascotas()) {
                System.out.println(owner);
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar propietarios. Intenta nuevamente.");
        }
    }
    
    public void RegistrarMascota(Owner owner) {
        try {
            System.out.println("=========================================");
            System.out.println("        Registro de Nueva Mascota        ");
            System.out.println("=========================================");

            System.out.println("\n¿Qué tipo de mascota vas a registrar?");
            System.out.println("1. Loro");
            System.out.println("2. Perro");
            System.out.println("3. Gato");
            System.out.println("4. Hamster");
            System.out.print("Elige una opción (1-4): ");
            int tipoMascota = Integer.parseInt(scanner.nextLine());

            System.out.print("\nIngresa el nombre de la mascota: ");
            String name = scanner.nextLine();

            System.out.print("Ingresa la raza de la mascota: ");
            String breed = scanner.nextLine();

            System.out.print("Ingresa la fecha de nacimiento (yyyy-MM-dd): ");
            LocalDate birthDate = LocalDate.parse(scanner.nextLine());

            System.out.println("\nSelecciona el sexo de la mascota:");
            for (Sex s : Sex.values()) {
                System.out.println(s.getIdSex() + ". " + s.getSex());
            }
            System.out.print("Elige una opción: ");
            int sexChoice = Integer.parseInt(scanner.nextLine());
            Sex sex = Sex.values()[sexChoice - 1];

            System.out.print("\nIngresa el número de microchip: ");
            String microchip = scanner.nextLine();

            System.out.print("Ingresa la URL de la foto de la mascota (opcional): ");
            String photo = scanner.nextLine();

            System.out.println("\nSelecciona el estatus de la mascota:");
            for (PetStatus status : PetStatus.values()) {
                System.out.println(status.getIdStatus() + ". " + status.getStatus());
            }
            System.out.print("Elige una opción: ");
            int statusChoice = Integer.parseInt(scanner.nextLine());
            PetStatus petStatus = PetStatus.values()[statusChoice - 1];

            Animal newAnimal = null;

            switch (tipoMascota) {
                case 1:
                    newAnimal = new Loro(name, breed, birthDate, sex, microchip, photo.isEmpty() ? null : photo, petStatus, owner);
                    break;
                case 2:
                    newAnimal = new Perro(name, breed, birthDate, sex, microchip, photo.isEmpty() ? null : photo, petStatus, owner);
                    break;
                case 3:
                    newAnimal = new Gato(name, breed, birthDate, sex, microchip, photo.isEmpty() ? null : photo, petStatus, owner);
                    break;
                case 4:
                    newAnimal = new Hamster(name, breed, birthDate, sex, microchip, photo.isEmpty() ? null : photo, petStatus, owner);
                    break;
                default:
                    System.out.println("\n¡Opción no válida! Por favor, selecciona un tipo de mascota válido.");
                    return;
            }

            boolean petInserted = AdminController.insertarMascota(newAnimal, owner);
            if (petInserted) {
                System.out.println("\nMascota registrada correctamente: " + name);
            } else {
                System.out.println("\nHubo un problema al registrar la mascota.");
            }
        } catch (Exception e) {
            System.out.println("\nError al registrar mascota. Intenta nuevamente.");
        }
    }
}
