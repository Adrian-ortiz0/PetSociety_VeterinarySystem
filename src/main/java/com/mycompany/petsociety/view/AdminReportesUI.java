package com.mycompany.petsociety.view;
import com.mycompany.petsociety.controllers.HistorialesController;
import com.mycompany.petsociety.controllers.PetController;
import com.mycompany.petsociety.controllers.VacunasController;
import com.mycompany.petsociety.models.Admin;
import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.HistorialServicios;
import com.mycompany.petsociety.models.Servicio;
import com.mycompany.petsociety.models.Vacuna;
import com.mycompany.petsociety.models.Veterinarian;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AdminReportesUI {
    private Scanner scanner;
    
    
    public AdminReportesUI(Admin admin){
        this.scanner = new Scanner(System.in);
    }
    
    public void menuReportes(){
        OUTER:
        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println();
            System.out.println("Que reportes deseas ver: ");
            System.out.println("1. Mascotas");
            System.out.println("2. Top 3 servicios mas populares");
            System.out.println("3. Veterinarios con mas servicios");
            System.out.println("4. Total facturado por periodo");
            System.out.println("0. Salir");
            System.out.println();
            System.out.println("----------------------------------------------");
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 0:
                    System.out.println("Saliendo");
                    break OUTER;
                case 1:
                    menuReportesMascotas();
                    break;
                case 2:
                    serviciosMasPopulares();
                    break;
                case 3:
                    veterinariosMasPopulares();
                    break;
                case 4:
                    verFacturas();
                    break;
                default:
                    break;
            }
        }
        
    }
    
    public void verFacturas() {
    HashMap<String, Object> totalesMensuales = HistorialesController.calcularTotalesMensuales();
    
    if (totalesMensuales.isEmpty()) {
        System.out.println("No se han encontrado facturas para mostrar.");
        return;
    }

    System.out.println("Totales facturados por mes:");
    totalesMensuales.forEach((mes, total) -> {
        System.out.println("Mes: " + mes + " - Total Facturado: " + total);
    });
}
    
    public void veterinariosMasPopulares(){
        ArrayList<Veterinarian> veterinariosTop = HistorialesController.veterinariosMasSolicitados();
        for(int i = 0; i < veterinariosTop.size(); i++){
            Veterinarian vet = veterinariosTop.get(i);
            System.out.println((i + 1) + ". " + vet.getNombre() + " | Especialidad: " + vet.getEspecialidad());
        }
    }
    
    public void serviciosMasPopulares(){
        ArrayList<Servicio> serviciosPopulares = HistorialesController.serviciosMasSolicitados();
        for(int i = 0; i < serviciosPopulares.size(); i++){
            Servicio servicioElegido = serviciosPopulares.get(i);
            System.out.println((i + 1) + ". "+ servicioElegido.getTipoServicio());
        }
    }
    
    public void menuReportesMascotas(){
        OUTER:
        while (true) {
            System.out.println("Que deseas ver: ");
            System.out.println("1. Numero de visitas");
            System.out.println("2. Procedimientos realizados");
            System.out.println("3. Vacunas aplicadas");
            System.out.println("0. Salir");
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 0:
                    System.out.println("Saliendo...");
                    break OUTER;
                case 1:
                    numeroDeVisitas();
                    break;
                case 2:
                    procedimientosRealizados();
                    break;
                case 3:
                    vacunasAplicadas();
                    break;
                default:
                    System.out.println("No existe esa opcion");
                    break;
            }
        }
    }
    
    public void vacunasAplicadas(){
        while(true){
            System.out.println("=====   Lista de todos los animales   ======");
            ArrayList<Animal> listaAnimales = PetController.mostrarAnimalesConDueño();

            for (int i = 0; i < listaAnimales.size(); i++) {
                System.out.println();
                Animal animal = listaAnimales.get(i);
                System.out.println((i + 1) + ". " + "Nombre mascota: " + animal.getName() +
                                   " | Nombre Dueño: " + animal.getOwner().getName() +
                                   " | Especie: " + animal.getSpecie());
            }
            System.out.println("0. para salir");

            System.out.print("Selecciona el número de la mascota para ver sus vacunas aplicadas (o 0 para salir): ");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 0) {
                System.out.println("Saliendo...");
                break;
            }
            Animal animalElegido = null;
            if (opcion > 0 && opcion <= listaAnimales.size()) {
                animalElegido = listaAnimales.get(opcion - 1);
            }
            ArrayList<Vacuna> vacunas = VacunasController.mostrarTodasLasVacunasDeMascota(animalElegido);
            for(int i = 0; i < vacunas.size(); i++){
                System.out.println();
                System.out.println((i + 1) + ". " + vacunas.get(i).getNombre() + " | Fecha Aplicacion: " + vacunas.get(i).getFechaAplicacion());
                System.out.println();
            }
        }
    }
    
    public void procedimientosRealizados(){
        while(true){
            System.out.println("=====   Lista de todos los animales   ======");
            ArrayList<Animal> listaAnimales = PetController.mostrarAnimalesConDueño();

            for (int i = 0; i < listaAnimales.size(); i++) {
                System.out.println();
                Animal animal = listaAnimales.get(i);
                System.out.println((i + 1) + ". " + "Nombre mascota: " + animal.getName() +
                                   " | Nombre Dueño: " + animal.getOwner().getName() +
                                   " | Especie: " + animal.getSpecie());
            }
            System.out.println("0. para salir");

            System.out.print("Selecciona el número de la mascota para ver sus visitas (o 0 para salir): ");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 0) {
                System.out.println("Saliendo...");
                break;
            }
            Animal animalElegido = null;
            if (opcion > 0 && opcion <= listaAnimales.size()) {
                animalElegido = listaAnimales.get(opcion - 1);
            }
            ArrayList<HistorialServicios> historialProcedimientos = HistorialesController.mostrarServicios(animalElegido);
            for (int i = 0; i < historialProcedimientos.size(); i++) {
                HistorialServicios hist = historialProcedimientos.get(i);
                System.out.println((i + 1) + ". " + hist.getServicio().getNombre() + "Fecha Aplicacion: " + hist.getFechaAplicacion());
            }
        }
    }
    
    public void numeroDeVisitas() {
        while (true) {
            System.out.println("=====   Lista de todos los animales   ======");
            ArrayList<Animal> listaAnimales = PetController.mostrarAnimalesConDueño();

            for (int i = 0; i < listaAnimales.size(); i++) {
                Animal animal = listaAnimales.get(i);
                System.out.println((i + 1) + ". " + "Nombre mascota: " + animal.getName() +
                                   " | Nombre Dueño: " + animal.getOwner().getName() +
                                   " | Especie: " + animal.getSpecie());
            }
            System.out.println("0. para salir");

            System.out.print("Selecciona el número de la mascota para ver sus visitas (o 0 para salir): ");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 0) {
                System.out.println("Saliendo...");
                break;
            }

            if (opcion > 0 && opcion <= listaAnimales.size()) {
                Animal animalElegido = listaAnimales.get(opcion - 1);
                int numeroVisitas = HistorialesController.numeroDeVisitasPorMascota(animalElegido);
                System.out.println("La mascota " + animalElegido.getName() + 
                                   " tiene " + numeroVisitas + " visitas registradas.");
            } else {
                System.out.println("Opción inválida. Por favor, intenta nuevamente.");
            }
        }
    }
}
