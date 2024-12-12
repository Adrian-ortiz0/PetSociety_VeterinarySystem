package com.mycompany.petsociety.view;

import com.mycompany.petsociety.controllers.InsumosController;
import com.mycompany.petsociety.controllers.ProveedorController;
import com.mycompany.petsociety.models.Admin;
import com.mycompany.petsociety.models.CategoriaInsumos;
import com.mycompany.petsociety.models.Insumo;
import com.mycompany.petsociety.models.MaterialMedico;
import com.mycompany.petsociety.models.Medicamento;
import com.mycompany.petsociety.models.Proveedor;
import com.mycompany.petsociety.models.TipoAnimalInsumo;
import com.mycompany.petsociety.models.Vacuna;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminInventaryUI {

    private Scanner scanner;

    public AdminInventaryUI(Admin admin) {
        this.scanner = new Scanner(System.in);
    }

    public void menuGestionInventario(Admin admin) {
    while (true) {
        try {
            System.out.println("=== Menu de Gestion de Inventarios ===");
            System.out.println("1. Inventario de Insumos Medicos");
            System.out.println("2. Alertas de Vencimiento");
            System.out.println("3. Reabastecimiento");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    menuInventarioInsumosMedicos(admin);
                    break;
                case 2:
                    menuAlertasVencimiento(admin);
                    break;
                case 3:
                    menuReabastecimiento();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    return;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Debe ingresar un numero.");
        }
    }
}

    private void menuInventarioInsumosMedicos(Admin admin) {
        while (true) {
            try {
                System.out.println("=== Inventario de Insumos Medicos ===");
                System.out.println("1. Registrar Medicamento");
                System.out.println("2. Registrar Vacuna");
                System.out.println("3. Registrar Material Medico");
                System.out.println("0. Volver");
                System.out.print("Seleccione una opcion: ");
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarMedicamento();
                        break;
                    case 2:
                        registrarVacuna();
                        break;
                    case 3:
                        registrarMaterialMedico();
                        break;
                    case 0:
                        System.out.println("Volviendo al menu principal...");
                        return;
                    default:
                        System.out.println("Opcion no valida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida. Debe ingresar un numero.");
            }
        }
    }

    public void menuAlertasVencimiento(Admin admin) {
        while (true) {
            try {
                System.out.println("=== Menu de Alertas de Vencimiento ===");
                System.out.println("1. Alertas de Medicamentos");
                System.out.println("2. Alertas de Vacunas");
                System.out.println("3. Volver al menu principal");
                System.out.print("Seleccione una opcion: ");
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        mostrarAlertasDeVencimientoMedicamentos();
                        break;
                    case 2:
                        mostrarAlertasDeVencimientoVacunas();
                        break;
                    case 3:
                        System.out.println("Volviendo al menu principal...");
                        return; 
                    default:
                        System.out.println("Opcion no valida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida. Debe ingresar un numero.");
            }
        }
    }
    
private void mostrarAlertasDeVencimientoMedicamentos() {
    try {
        System.out.print("Ingrese los dias para alertas de vencimiento: ");
        int diasAntes = Integer.parseInt(scanner.nextLine());

        ArrayList<Medicamento> alertas = InsumosController.obtenerAlertasDeVencimientoMedicamentos(diasAntes);

        if (alertas.isEmpty()) {
            System.out.println("No hay alertas de vencimiento para medicamentos en los proximos " + diasAntes + " dias.");
        } else {
            System.out.println("=== Alertas de Vencimiento para Medicamentos ===");
            for (Medicamento medicamento : alertas) {
                System.out.println(medicamento);
            }
        }
    } catch (NumberFormatException e) {
        System.out.println("Entrada no valida. Debe ingresar un numero.");
    } catch (Exception e) {
        System.out.println("Ha ocurrido un error: " + e.getMessage());
    }
}

private void mostrarAlertasDeVencimientoVacunas() {
    try {
        System.out.print("Ingrese los dias para alertas de vencimiento: ");
        int diasAntes = Integer.parseInt(scanner.nextLine());

        ArrayList<Vacuna> alertas = InsumosController.obtenerAlertasDeVencimientoVacunas(diasAntes);

        if (alertas.isEmpty()) {
            System.out.println("No hay alertas de vencimiento para vacunas en los proximos " + diasAntes + " dias.");
        } else {
            System.out.println("=== Alertas de Vencimiento para Vacunas ===");
            for (Vacuna vacuna : alertas) {
                System.out.println(vacuna);
            }
        }
    } catch (NumberFormatException e) {
        System.out.println("Entrada no valida. Debe ingresar un numero.");
    } catch (Exception e) {
        System.out.println("Ha ocurrido un error: " + e.getMessage());
    }
}
    private void menuReabastecimiento() {
    try {
        System.out.println("=== Reabastecimiento ===");

        ArrayList<Insumo> productosParaReabastecer = InsumosController.obtenerProductosParaReabastecer();

        if (productosParaReabastecer.isEmpty()) {
            System.out.println("No hay productos que necesiten reabastecerse.");
        } else {
            System.out.println("Productos que necesitan reabastecerse:");
            for (Insumo insumo : productosParaReabastecer) {
                System.out.println("ID: " + insumo.getId() + ", Nombre: " + insumo.getNombre() +
                                   ", Stock: " + insumo.getCantidadStock() +
                                   ", Nivel Minimo: " + insumo.getNivelMinimo());
            }

            System.out.println("Generando orden de compra...");
            InsumosController.generarOrdenDeCompraAutomatica(productosParaReabastecer);
            int ultimaOrdenId = InsumosController.obtenerUltimaOrdenGenerada();

            if (ultimaOrdenId != -1) {
                System.out.println("Actualizando stock con la orden ID: " + ultimaOrdenId);
                InsumosController.actualizarStock(ultimaOrdenId);
            } else {
                System.out.println("No se pudo recuperar la ultima orden generada.");
            }
        }
    } catch (Exception e) {
        System.out.println("Ha ocurrido un error: " + e.getMessage());
    }
}

    private void registrarMedicamento() {
        try {
            System.out.println("=== Registrar Medicamento ===");
            System.out.print("Ingrese el nombre del medicamento: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el tipo (antibiótico, antiinflamatorio, etc.): ");
            String tipo = scanner.nextLine();

            System.out.print("Ingrese el fabricante: ");
            String fabricante = scanner.nextLine();

            System.out.print("Ingrese la cantidad en stock: ");
            int cantidadStock = Integer.parseInt(scanner.nextLine());

            System.out.print("Ingrese el nivel mínimo permitido: ");
            int nivelMinimo = Integer.parseInt(scanner.nextLine());

            System.out.print("Ingrese la fecha de vencimiento (yyyy-MM-dd): ");
            String fechaVencimientoStr = scanner.nextLine();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr, dateFormatter);

            CategoriaInsumos categoriaSeleccionada = InsumosController.obtenerCategoriaPorId(1);

            System.out.println("Seleccione la unidad de medida del medicamento: ");
            System.out.println("1. mg");
            System.out.println("2. g");
            System.out.println("3. ml");
            System.out.println("4. L");
            System.out.print("Ingrese el número de la unidad de medida: ");
            int medidaOpcion = Integer.parseInt(scanner.nextLine());

            String medida = null;
            switch (medidaOpcion) {
                case 1:
                    medida = "mg";
                    break;
                case 2:
                    medida = "g";
                    break;
                case 3:
                    medida = "ml";
                    break;
                case 4:
                    medida = "L";
                    break;
                default:
                    System.out.println("Opción no válida.");
                    return;
            }

            System.out.println("Ingrese el precio unitario: ");
            Double precioUnitaroDouble = Double.valueOf(scanner.nextLine());
            BigDecimal precioUnitario = BigDecimal.valueOf(precioUnitaroDouble);

            System.out.println("Seleccione la especie para la cual es el medicamento:");
            int i = 1;
            for (TipoAnimalInsumo especie : TipoAnimalInsumo.values()) {
                System.out.println(i++ + ". " + especie);
            }
            System.out.print("Ingrese el número de la especie: ");
            int especieOpcion = Integer.parseInt(scanner.nextLine());

            String especie = null;
            TipoAnimalInsumo[] especies = TipoAnimalInsumo.values();
            if (especieOpcion >= 1 && especieOpcion <= especies.length) {
                especie = especies[especieOpcion - 1].name();
            } else {
                System.out.println("Opción no válida.");
                return;
            }

            System.out.println("Seleccione el proveedor para el medicamento:");
            ArrayList<Proveedor> proveedores = ProveedorController.obtenerTodosProveedores(); // Obtener todos los proveedores
            for (int j = 0; j < proveedores.size(); j++) {
                Proveedor proveedor = proveedores.get(j);
                System.out.println((j + 1) + ". " + proveedor.getNombre());
            }
            System.out.print("Ingrese el número del proveedor: ");
            int proveedorOpcion = Integer.parseInt(scanner.nextLine());

            Proveedor proveedorSeleccionado = proveedores.get(proveedorOpcion - 1);

            Medicamento medicamento = new Medicamento(
                tipo,            
                especie,
                fechaVencimiento,  
                0,
                nombre,         
                fabricante,       
                medida,          
                categoriaSeleccionada, 
                nivelMinimo,
                cantidadStock,
                precioUnitario,
                proveedorSeleccionado
            );

            boolean exito = InsumosController.registrarMedicamentos(medicamento);

            if (exito) {
                System.out.println("Medicamento registrado con éxito.");
            } else {
                System.out.println("Hubo un error al registrar el medicamento.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error de formato en los datos ingresados. Por favor, ingrese valores válidos.");
        } catch (DateTimeParseException e) {
            System.out.println("Error en el formato de la fecha. Asegúrese de usar el formato yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
        }
    }

    private void registrarVacuna() {
        try {
            System.out.println("=== Registrar Vacuna ===");
            System.out.print("Ingrese el nombre de la vacuna: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el tipo (ejemplo: prevención, tratamiento, etc.): ");
            String tipo = scanner.nextLine();

            System.out.print("Ingrese el fabricante: ");
            String fabricante = scanner.nextLine();

            System.out.print("Ingrese la cantidad en stock: ");
            int cantidadStock = Integer.parseInt(scanner.nextLine());

            System.out.print("Ingrese el nivel mínimo permitido: ");
            int nivelMinimo = Integer.parseInt(scanner.nextLine());

            System.out.print("Ingrese la fecha de vencimiento (yyyy-MM-dd): ");
            String fechaVencimientoStr = scanner.nextLine();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr, dateFormatter);

            CategoriaInsumos categoriaSeleccionada = InsumosController.obtenerCategoriaPorId(2); 

            System.out.println("Seleccione la unidad de medida de la vacuna: ");
            System.out.println("1. mg");
            System.out.println("2. ml");
            System.out.println("3. dosis");
            System.out.print("Ingrese el número de la unidad de medida: ");
            int medidaOpcion = Integer.parseInt(scanner.nextLine());

            String medida = null;
            switch (medidaOpcion) {
                case 1:
                    medida = "mg";
                    break;
                case 2:
                    medida = "ml";
                    break;
                case 3:
                    medida = "dosis";
                    break;
                default:
                    System.out.println("Opción no válida.");
                    return;
            }

            System.out.println("Ingrese el precio unitario: ");
            Double precioUnitaroDouble = Double.valueOf(scanner.nextLine());
            BigDecimal precioUnitario = BigDecimal.valueOf(precioUnitaroDouble);

            System.out.println("Seleccione la especie para la cual es la vacuna:");
            int i = 1;
            for (TipoAnimalInsumo especie : TipoAnimalInsumo.values()) {
                System.out.println(i++ + ". " + especie);
            }
            System.out.print("Ingrese el número de la especie: ");
            int especieOpcion = Integer.parseInt(scanner.nextLine());

            String especie = null;
            TipoAnimalInsumo[] especies = TipoAnimalInsumo.values();
            if (especieOpcion >= 1 && especieOpcion <= especies.length) {
                especie = especies[especieOpcion - 1].name();
            } else {
                System.out.println("Opción no válida.");
                return;
            }

            System.out.println("Seleccione el proveedor del medicamento:");
            ArrayList<Proveedor> proveedores = InsumosController.obtenerProveedores(); 
            int j = 1;
            for (Proveedor proveedor : proveedores) {
                System.out.println(j++ + ". " + proveedor.getNombre());
            }
            System.out.print("Ingrese el número del proveedor: ");
            int proveedorOpcion = Integer.parseInt(scanner.nextLine());

            Proveedor proveedorSeleccionado = null;
            if (proveedorOpcion >= 1 && proveedorOpcion <= proveedores.size()) {
                proveedorSeleccionado = proveedores.get(proveedorOpcion - 1);
            } else {
                System.out.println("Opción no válida.");
                return;
            }

            System.out.print("Ingrese el lote de la vacuna: ");
            String lote = scanner.nextLine();
            LocalDate fechaIngreso = LocalDate.now();

            Vacuna vacuna = new Vacuna(
                especie,
                lote,
                fechaIngreso,
                fechaVencimiento,
                0,                  
                nombre,
                fabricante,
                medida,
                categoriaSeleccionada,
                nivelMinimo,
                cantidadStock,
                precioUnitario,
                proveedorSeleccionado
            );

            boolean exito = InsumosController.registrarVacunas(vacuna);

            if (exito) {
                System.out.println("Vacuna registrada con éxito.");
            } else {
                System.out.println("Hubo un error al registrar la vacuna.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error de formato en los datos ingresados. Por favor, ingrese valores válidos.");
        } catch (DateTimeParseException e) {
            System.out.println("Error en el formato de la fecha. Asegúrese de usar el formato yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
        }
    }
    private void registrarMaterialMedico() {
        try {
            System.out.println("=== Registrar Material Médico ===");

            System.out.print("Ingrese el nombre del material médico: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el tipo de material (ejemplo: quirúrgico, diagnóstico, etc.): ");
            String tipo = scanner.nextLine();

            System.out.print("Ingrese el fabricante: ");
            String fabricante = scanner.nextLine();

            System.out.print("Ingrese la cantidad en stock: ");
            int cantidadStock = Integer.parseInt(scanner.nextLine());

            System.out.print("Ingrese el nivel mínimo permitido: ");
            int nivelMinimo = Integer.parseInt(scanner.nextLine());

            System.out.println("Seleccione la unidad de medida del material médico: ");
            System.out.println("1. mg");
            System.out.println("2. ml");
            System.out.println("3. dosis");
            System.out.print("Ingrese el número de la unidad de medida: ");
            int medidaOpcion = Integer.parseInt(scanner.nextLine());

            String medida = null;
            switch (medidaOpcion) {
                case 1:
                    medida = "mg";
                    break;
                case 2:
                    medida = "ml";
                    break;
                case 3:
                    medida = "dosis";
                    break;
                default:
                    System.out.println("Opción no válida.");
                    return;
            }

            System.out.println("Ingrese el precio unitario: ");
            Double precioUnitaroDouble = Double.valueOf(scanner.nextLine());
            BigDecimal precioUnitario = BigDecimal.valueOf(precioUnitaroDouble);

            CategoriaInsumos categoriaSeleccionada = InsumosController.obtenerCategoriaPorId(3);

            System.out.println("Seleccione el proveedor del material médico:");
            ArrayList<Proveedor> proveedores = InsumosController.obtenerProveedores();
            int j = 1;
            for (Proveedor proveedor : proveedores) {
                System.out.println(j++ + ". " + proveedor.getNombre());
            }
            System.out.print("Ingrese el número del proveedor: ");
            int proveedorOpcion = Integer.parseInt(scanner.nextLine());

            Proveedor proveedorSeleccionado = null;
            if (proveedorOpcion >= 1 && proveedorOpcion <= proveedores.size()) {
                proveedorSeleccionado = proveedores.get(proveedorOpcion - 1);
            } else {
                System.out.println("Opción no válida.");
                return;
            }

            System.out.print("Ingrese el número de unidades del material médico: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            MaterialMedico materialMedico = new MaterialMedico(
                tipo,         
                cantidad,        
                0,                
                nombre,            
                fabricante,      
                medida,     
                categoriaSeleccionada,
                nivelMinimo,     
                cantidadStock,   
                precioUnitario,   
                proveedorSeleccionado 
            );

            boolean exito = InsumosController.registrarMaterialMedico(materialMedico);

            if (exito) {
                System.out.println("Material médico registrado con éxito.");
            } else {
                System.out.println("Hubo un error al registrar el material médico.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error de formato en los datos ingresados. Por favor, ingrese valores válidos.");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
        }
    }

    private String seleccionarUnidadMedida() {
        System.out.println("Seleccione la unidad de medida del material:");
        System.out.println("1. Unidad");
        System.out.println("2. Caja");
        System.out.println("3. Paquete");

        while (true) {
            try {
                System.out.print("Ingrese el número de la unidad de medida: ");
                int medidaOpcion = Integer.parseInt(scanner.nextLine());
                switch (medidaOpcion) {
                    case 1:
                        return "Unidad";
                    case 2:
                        return "Caja";
                    case 3:
                        return "Paquete";
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número válido.");
            }
        }
    }

}
