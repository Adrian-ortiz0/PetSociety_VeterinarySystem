package com.mycompany.petsociety.view;

import com.mycompany.petsociety.controllers.DateController;
import com.mycompany.petsociety.controllers.OwnerController;
import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.Cita;
import com.mycompany.petsociety.models.EstadoCita;
import com.mycompany.petsociety.models.Owner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class OwnersAgendasUI {
    
    private Scanner scanner;
    
    public OwnersAgendasUI(Owner owner){
        this.scanner = new Scanner(System.in);
    }
    
   public void agendarCita(Owner owner) {
        try {
            ArrayList<Animal> mascotasOwner = OwnerController.obtenerMascotasDePropietario(owner);
            if (mascotasOwner.isEmpty()) {
                System.out.println("No tienes mascotas registradas.");
                return;
            }

            for (int i = 0; i < mascotasOwner.size(); i++) {
                Animal mascota = mascotasOwner.get(i);
                System.out.println((i + 1) + ". " + mascota.getName());
            }

            int opcionMascota = obtenerOpcionUsuario(1, mascotasOwner.size(), "¿Qué mascota deseas para la cita?");
            Animal animal = mascotasOwner.get(opcionMascota - 1);
            System.out.println("Has seleccionado a " + animal.getName());

            System.out.println("Ingresa la fecha para la cita (formato: yyyy-MM-dd): ");
            String fechaStr = scanner.nextLine();
            LocalDate fechaCita = LocalDate.parse(fechaStr);

            System.out.println("Ingresa la hora para la cita (formato: HH:mm): ");
            String horaStr = scanner.nextLine();
            LocalTime horaCita = LocalTime.parse(horaStr);

            EstadoCita estadoCita = null;

            Cita cita = new Cita(animal.getId(), animal, fechaCita, horaCita, estadoCita.Programada);

            if (DateController.insertarCitas(cita)) {
                System.out.println("Cita agendada con éxito.");
            } else {
                System.out.println("Hubo un error al agendar la cita.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado al agendar la cita: " + e.getMessage());
        }
    }

    public int obtenerOpcionUsuario(int min, int max, String mensaje) {
        int opcion = -1;
        while (true) {
            try {
                System.out.println(mensaje);
                opcion = Integer.parseInt(scanner.nextLine());
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
}