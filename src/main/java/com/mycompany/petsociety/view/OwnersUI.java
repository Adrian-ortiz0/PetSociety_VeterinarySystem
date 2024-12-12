package com.mycompany.petsociety.view;

import com.mycompany.petsociety.controllers.FacturaGenerator;
import com.mycompany.petsociety.controllers.HistorialesController;
import com.mycompany.petsociety.controllers.InsumosController;
import com.mycompany.petsociety.controllers.OwnerController;
import com.mycompany.petsociety.controllers.PetController;
import com.mycompany.petsociety.controllers.ServiciosController;
import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.HistorialServicios;
import com.mycompany.petsociety.models.HistorialVacunacion;
import com.mycompany.petsociety.models.Medicamento;
import com.mycompany.petsociety.models.Owner;
import com.mycompany.petsociety.models.PetHistory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OwnersUI {
    private Scanner scanner;

    public OwnersUI(Owner owner) {
        this.scanner = new Scanner(System.in);
    }

    public void menuUsuario(Owner owner) {
        System.out.println("Bienvenido usuario: " + owner.getName());
        System.out.println("======================================");
        System.out.println();
        OUTER:
        while (true) {
            try {
                System.out.println("Que deseas hacer?");
                System.out.println("--------------------------------------------");
                System.out.println();
                System.out.println("1. Ver historial medico");
                System.out.println("2. Agendar citas");
                System.out.println("3. Servicios");
                System.out.println("4. Ver mascotas disponibles para adopcion");
                System.out.println("5. Comprar medicamentos");
                System.out.println("0. Cerrar sesion");
                System.out.println();
                System.out.println("--------------------------------------------");
                int input = Integer.parseInt(scanner.nextLine());
                switch (input) {
                    case 0:
                        System.out.println("Saliendo...");
                        break OUTER;
                    case 1:
                        menuHistorialMedico(owner);
                        break;
                    case 2:
                        OwnersAgendasUI ownerAgendaUI = new OwnersAgendasUI(owner);
                        ownerAgendaUI.agendarCita(owner);
                        break;
                    case 3:
                        OwnerCitasUI ownerCitasUI = new OwnerCitasUI(owner);
                        ownerCitasUI.menuAdminCitas(owner);
                        break;
                    case 4:
                        verMascotasParaAdopcion(owner);
                        break;
                    case 5:
                        comprarMeds(owner);
                        break;
                    default:
                        System.out.println("No existe opcion");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
    }

    public void comprarMeds(Owner owner) {
        try {
            System.out.println("Medicamentos para venta: ");
            ArrayList<Medicamento> medicamentos = InsumosController.mostrarMedicamentosParaVenta();

            if (medicamentos.isEmpty()) {
                System.out.println("No hay medicamentos disponibles para la venta.");
                return;
            }

            ArrayList<Medicamento> carrito = new ArrayList<>();
            ArrayList<Integer> cantidades = new ArrayList<>();

            while (true) {
                System.out.println("\nLista de medicamentos disponibles:");
                for (int i = 0; i < medicamentos.size(); i++) {
                    Medicamento medicamento = medicamentos.get(i);
                    System.out.println((i + 1) + ". " + medicamento.getNombre() + " - Precio: " + medicamento.getPrecioUnitario() + " - Stock: " + medicamento.getCantidadStock());
                }

                System.out.println("\nSeleccione el número del medicamento que desea comprar (o presione 0 para finalizar):");
                int opcion = scanner.nextInt();

                if (opcion == 0) {
                    break;
                }

                if (opcion < 1 || opcion > medicamentos.size()) {
                    System.out.println("Opción no válida. Intente nuevamente.");
                    continue;
                }

                Medicamento seleccionado = medicamentos.get(opcion - 1);

                System.out.println("Ingrese la cantidad que desea comprar del medicamento: " + seleccionado.getNombre());
                int cantidad = scanner.nextInt();

                if (cantidad <= 0) {
                    System.out.println("Cantidad no válida. Intente nuevamente.");
                    continue;
                }

                if (cantidad > seleccionado.getCantidadStock()) {
                    System.out.println("No hay suficiente stock para esa cantidad. Stock disponible: " + seleccionado.getCantidadStock());
                    continue;
                }

                carrito.add(seleccionado);
                cantidades.add(cantidad);

                System.out.println("Añadido al carrito: " + seleccionado.getNombre() + " - Cantidad: " + cantidad);
            }

            if (carrito.isEmpty()) {
                System.out.println("No se seleccionaron medicamentos para la compra.");
            } else {
                System.out.println("\nResumen de la compra:");
                BigDecimal total = BigDecimal.ZERO;
                for (int i = 0; i < carrito.size(); i++) {
                    Medicamento medicamento = carrito.get(i);
                    int cantidad = cantidades.get(i);
                    BigDecimal costo = medicamento.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidad));
                    total = total.add(costo);

                    System.out.println(medicamento.getNombre() + " - Cantidad: " + cantidad + " - Subtotal: " + costo);

                    boolean actualizado = InsumosController.restarCantidadInsumos(medicamento.getId(), cantidad);
                    if (actualizado) {
                        medicamento.setCantidadStock(medicamento.getCantidadStock() - cantidad);
                    } else {
                        System.out.println("Error al actualizar el stock del medicamento: " + medicamento.getNombre());
                    }
                }

                System.out.println("Total a pagar: " + total);
                FacturaGenerator.generarFacturaPDFCompras(owner.getName(), owner.getCedula(), total, carrito);
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al procesar la compra: " + e.getMessage());
        }
    }

    public void verMascotasParaAdopcion(Owner owner) {
        try {
            ArrayList<Animal> listaAdopcion = PetController.mostrarAnimalesParaAdoptar();

            if (listaAdopcion.isEmpty()) {
                System.out.println("No hay mascotas disponibles para adopcion en este momento.");
                return;
            }

            System.out.println("Mascotas disponibles para adopcion:");
            for (int i = 0; i < listaAdopcion.size(); i++) {
                Animal animal = listaAdopcion.get(i);
                System.out.println((i + 1) + ". Nombre: " + animal.getName() + ", Especie: " + animal.getSpecie());
            }

            System.out.println("Seleccione el numero de la mascota que desea adoptar (o ingrese 0 para salir):");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 0) {
                System.out.println("Saliendo del menu de adopciones.");
                return;
            }

            if (opcion > 0 && opcion <= listaAdopcion.size()) {
                Animal seleccionada = listaAdopcion.get(opcion - 1);
                System.out.println("Ha seleccionado adoptar a: " + seleccionada.getName() + " (" + seleccionada.getSpecie() + ")");
                OwnerController.adoptarMascota(owner);
                ServiciosController.registrarAdopcion(owner, seleccionada);
            } else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida. Por favor, ingrese un número.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error al procesar la adopción: " + e.getMessage());
        }
    }

    public void agendarCitas() {
        System.out.println("Tus mascotas: ");
    }

    public void menuHistorialMedico(Owner owner) {
        try {
            while (true) {
                System.out.println("--------------------------------------------");
                System.out.println("Tus mascotas:");
                System.out.println();
                ArrayList<Animal> mascotas = OwnerController.obtenerMascotasDePropietario(owner);

                if (mascotas.isEmpty()) {
                    System.out.println("No tienes mascotas registradas.");
                    break;
                }

                for (int i = 0; i < mascotas.size(); i++) {
                    System.out.println((i + 1) + ". " + mascotas.get(i).getName());
                }

                System.out.println("0. Volver");
                System.out.println();
                System.out.println("--------------------------------------------");
                System.out.print("Selecciona una mascota para ver su historial medico: ");
                int opcion = Integer.parseInt(scanner.nextLine());

                if (opcion == 0) {
                    System.out.println("Volviendo al menu anterior...");
                    break;
                }

                if (opcion > 0 && opcion <= mascotas.size()) {
                    Animal mascotaSeleccionada = mascotas.get(opcion - 1);
                    historialClinico(mascotaSeleccionada);
                } else {
                    System.out.println("Opcion no valida. Intentalo de nuevo.");
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println("Entrada no valida. Por favor, ingresa un numero.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado al acceder al historial médico: " + e.getMessage());
        }
    }

    public void historialClinico(Animal pet) {
    OUTER:
    while (true) {
        try {
            System.out.println("==============================================");
            System.out.println("          Historial Clinico de " + pet.getName());
            System.out.println("==============================================");
            System.out.println("Que deseas consultar?");
            System.out.println(" 1. Historial de medidas");
            System.out.println(" 2. Historial de vacunas");
            System.out.println(" 3. Historial de servicios");
            System.out.println(" 4. Condiciones preexistentes");
            System.out.println(" 0. Volver");
            System.out.println("----------------------------------------------");
            
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion) {
                case 0:
                    System.out.println("\nVolviendo al menú anterior...");
                    break OUTER;
                case 1:
                    System.out.println("\nMostrando historial de medidas...");
                    mostrarHistorialMedidas(pet);
                    break;
                case 2:
                    System.out.println("\nMostrando historial de vacunas...");
                    mostrarHistorialVacunas(pet);
                    break;
                case 3:
                    System.out.println("\nMostrando historial de servicios...");
                    mostrarServicios(pet);
                    break;
                case 4:
                    System.out.println("\nMostrando condiciones preexistentes de la mascota...");
                    System.out.println("Condiciones de la mascota: ");
                    break;
                default:
                    System.out.println("\nOpción inválida. Por favor, elige una opción válida.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("\nOpción no válida. Intenta nuevamente.");
        } catch (Exception e) {
            System.out.println("\nError inesperado: " + e.getMessage());
        }
    }
}

    public void mostrarServicios(Animal pet) {
        try {
            ArrayList<HistorialServicios> historial = HistorialesController.mostrarServicios(pet);
            for (int i = 0; i < historial.size(); i++) {
                HistorialServicios hist = historial.get(i);
                System.out.println((i + 1) + ". " + hist.getServicio().getNombre() + "Fecha Aplicacion: " + hist.getFechaAplicacion());
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar servicios: " + e.getMessage());
        }
    }

    public void mostrarHistorialVacunas(Animal pet) {
        try {
            ArrayList<HistorialVacunacion> historial = HistorialesController.mostrarServiciosPorTipo(pet);
            for (int i = 0; i < historial.size(); i++) {
                HistorialVacunacion hist = historial.get(i);
                System.out.println((i + 1) + ". " + hist.getVacuna().getNombre() + " Fecha Aplicacion: " + hist.getFechaAplicacion());
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar historial de vacunas: " + e.getMessage());
        }
    }

    public void mostrarHistorialMedidas(Animal pet) {
        try {
            ArrayList<PetHistory> historial = OwnerController.verHistorialMedidasMascota(pet);

            if (historial.isEmpty()) {
                System.out.println("No se encontraron registros de medidas para esta mascota.");
            } else {
                for (PetHistory hist : historial) {
                    System.out.println("Peso: " + hist.getWeight() + ", Altura: " + hist.getHeight() + ", Fecha: " + hist.getRegisterDate());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar historial de medidas: " + e.getMessage());
        }
    }
}