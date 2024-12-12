package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.CategoriaInsumos;
import com.mycompany.petsociety.models.Insumo;
import com.mycompany.petsociety.models.MaterialMedico;
import com.mycompany.petsociety.models.Medicamento;
import com.mycompany.petsociety.models.Proveedor;
import com.mycompany.petsociety.models.TipoProveedor;
import com.mycompany.petsociety.models.Vacuna;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsumosController {
    
    public static boolean restarCantidadInsumos(int insumoId, int cantidad) {
    String sqlVerificarStock = "SELECT Stock FROM Insumos WHERE ID = ?";
    CRUD.setConexion(ConnectionDB.getConnection());
    Object[] paramsVerificar = {insumoId};
    
    ResultSet rs = CRUD.consultarDB(sqlVerificarStock, paramsVerificar);
    int stockActual = 0;

    try {
        if (rs != null && rs.next()) {
            stockActual = rs.getInt("Stock");
        }
    } catch (SQLException ex) {
        Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    if (stockActual < cantidad) {
        System.out.println("No hay suficiente stock disponible.");
        return false;
    }

    String sqlRestarStock = "UPDATE Insumos SET Stock = Stock - ? WHERE ID = ?";
    Object[] paramsRestar = {cantidad, insumoId};
    
    return CRUD.updateDB(sqlRestarStock, paramsRestar);
}
    
    public static ArrayList<Medicamento> mostrarMedicamentosPorTipo(String tipo) {
    CRUD.setConexion(ConnectionDB.getConnection());
    
    String sql = "SELECT m.ID, m.Tipo, m.TipoEspecie, m.FechaVencimiento, " +
                 "i.Nombre, i.Fabricante, i.UnidadMedida, i.CategoriaID, " +
                 "i.NivelMinimo, i.Stock, i.PrecioUnitario, i.ID_Proveedor " +
                 "FROM Medicamentos m " +
                 "JOIN Insumos i ON m.ID = i.ID " +
                 "WHERE m.Tipo = ?";
    
    ArrayList<Medicamento> medicamentosList = new ArrayList<>();
    
    try {
        ResultSet rs = CRUD.consultarDB(sql, tipo);
        
        if (rs != null) {
            while (rs.next()) {
                int idMedicamento = rs.getInt("ID");
                String medicamentoTipo = rs.getString("Tipo");
                String tipoEspecie = rs.getString("TipoEspecie");
                LocalDate fechaVencimiento = rs.getDate("FechaVencimiento").toLocalDate();
                
                String nombre = rs.getString("Nombre");
                String fabricante = rs.getString("Fabricante");
                String unidadMedida = rs.getString("UnidadMedida");
                int categoriaId = rs.getInt("CategoriaID");

                CategoriaInsumos categoria = CategoriaController.obtenerCategoriaPorId(categoriaId);
                int nivelMinimo = rs.getInt("NivelMinimo");
                int cantidadStock = rs.getInt("Stock");
                BigDecimal precioUnitario = rs.getBigDecimal("PrecioUnitario");

                int proveedorId = rs.getInt("ID_Proveedor");
                Proveedor proveedor = ProveedorController.obtenerProveedorPorId(proveedorId);
                
                Medicamento medicamento = new Medicamento(
                        medicamentoTipo,
                        tipoEspecie,
                        fechaVencimiento,
                        idMedicamento,
                        nombre,
                        fabricante,
                        unidadMedida,
                        categoria,
                        nivelMinimo,
                        cantidadStock,
                        precioUnitario,
                        proveedor
                );

                medicamentosList.add(medicamento);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return medicamentosList;
}
    public static ArrayList<Medicamento> mostrarMedicamentosParaVenta() {
        CRUD.setConexion(ConnectionDB.getConnection());

        String sql = "SELECT m.ID, m.Tipo, m.TipoEspecie, m.FechaVencimiento, " +
                     "i.Nombre, i.Fabricante, i.UnidadMedida, i.CategoriaID, " +
                     "i.NivelMinimo, i.Stock, i.PrecioUnitario, i.ID_Proveedor " +
                     "FROM Medicamentos m " +
                     "JOIN Insumos i ON m.ID = i.ID ";

        ArrayList<Medicamento> medicamentosList = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultarDB(sql);

            if (rs != null) {
                while (rs.next()) {
                    int idMedicamento = rs.getInt("ID");
                    String medicamentoTipo = rs.getString("Tipo");
                    String tipoEspecie = rs.getString("TipoEspecie");
                    LocalDate fechaVencimiento = rs.getDate("FechaVencimiento").toLocalDate();

                    String nombre = rs.getString("Nombre");
                    String fabricante = rs.getString("Fabricante");
                    String unidadMedida = rs.getString("UnidadMedida");
                    int categoriaId = rs.getInt("CategoriaID");

                    CategoriaInsumos categoria = CategoriaController.obtenerCategoriaPorId(categoriaId);
                    int nivelMinimo = rs.getInt("NivelMinimo");
                    int cantidadStock = rs.getInt("Stock");
                    BigDecimal precioUnitario = rs.getBigDecimal("PrecioUnitario");

                    int proveedorId = rs.getInt("ID_Proveedor");
                    Proveedor proveedor = ProveedorController.obtenerProveedorPorId(proveedorId);

                    Medicamento medicamento = new Medicamento(
                            medicamentoTipo,
                            tipoEspecie,
                            fechaVencimiento,
                            idMedicamento,
                            nombre,
                            fabricante,
                            unidadMedida,
                            categoria,
                            nivelMinimo,
                            cantidadStock,
                            precioUnitario,
                            proveedor
                    );

                    medicamentosList.add(medicamento);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return medicamentosList;
    }
    public static ArrayList<MaterialMedico> mostrarMaterialesMedicos() {
    CRUD.setConexion(ConnectionDB.getConnection());
    
    String sql = "SELECT mm.ID, mm.Tipo, " +
                 "i.Nombre, i.Fabricante, i.UnidadMedida, i.CategoriaID, " +
                 "i.NivelMinimo, i.Stock, i.PrecioUnitario, i.ID_Proveedor " +
                 "FROM Material_Medico mm " +
                 "JOIN Insumos i ON mm.ID = i.ID";
    
    ArrayList<MaterialMedico> materialesMedicosList = new ArrayList<>();
    
    try {
        ResultSet rs = CRUD.consultarDB(sql);
        
        if (rs != null) {
            while (rs.next()) {
                int idMaterialMedico = rs.getInt("ID");
                String materialTipo = rs.getString("Tipo");
                String nombre = rs.getString("Nombre");
                String fabricante = rs.getString("Fabricante");
                String unidadMedida = rs.getString("UnidadMedida");
                int categoriaId = rs.getInt("CategoriaID");

                CategoriaInsumos categoria = CategoriaController.obtenerCategoriaPorId(categoriaId);
                int nivelMinimo = rs.getInt("NivelMinimo");
                int cantidadStock = rs.getInt("Stock");
                BigDecimal precioUnitario = rs.getBigDecimal("PrecioUnitario");

                int proveedorId = rs.getInt("ID_Proveedor");
                Proveedor proveedor = ProveedorController.obtenerProveedorPorId(proveedorId);
                
                MaterialMedico materialMedico = new MaterialMedico(
                        materialTipo,
                        cantidadStock,
                        idMaterialMedico,
                        nombre,
                        fabricante,
                        unidadMedida,
                        categoria,
                        nivelMinimo,
                        cantidadStock,
                        precioUnitario,
                        proveedor
                );

                materialesMedicosList.add(materialMedico);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return materialesMedicosList;
}
    
    public static boolean registrarMedicamentos(Medicamento medicamento) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "INSERT INTO Insumos (Nombre, Fabricante, UnidadMedida, CategoriaID, NivelMinimo, Stock, PrecioUnitario, ID_Proveedor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    Object[] params = {
        medicamento.getNombre(),
        medicamento.getFabricante(),
        medicamento.getUnidadMedida(),
        medicamento.getCategoria().getId(),
        medicamento.getNivelMinimo(),
        medicamento.getCantidadStock(),
        medicamento.getPrecioUnitario(),
        medicamento.getProveedor().getId()
    };
    boolean exitoInsumo = CRUD.insertarDB(sql, params);

    if (exitoInsumo) {
        try {
            String sqlIdInsumo = "SELECT LAST_INSERT_ID()";
            ResultSet rs = CRUD.consultarDB(sqlIdInsumo);
            if (rs != null && rs.next()) {
                int insumoId = rs.getInt(1);

                String sqlMedicamento = "INSERT INTO Medicamentos (ID, Tipo, TipoEspecie, FechaVencimiento) VALUES (?, ?, ?, ?)";
                Object[] paramsMedicamento = {
                    insumoId,
                    medicamento.getTipo(),
                    medicamento.getTipoEspecie(),
                    medicamento.getFechaVencimiento()
                };

                return CRUD.insertarDB(sqlMedicamento, paramsMedicamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return false;
}
    public static boolean registrarVacunas(Vacuna vacuna) {
    CRUD.setConexion(ConnectionDB.getConnection());
    
    String sql = "INSERT INTO Insumos (Nombre, Fabricante, UnidadMedida, CategoriaID, NivelMinimo, Stock, PrecioUnitario, ID_Proveedor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    Object[] params = {
        vacuna.getNombre(),
        vacuna.getFabricante(),
        vacuna.getUnidadMedida(),
        2,
        vacuna.getNivelMinimo(),
        vacuna.getCantidadStock(),
        vacuna.getPrecioUnitario(),
        vacuna.getProveedor().getId()
    };
    boolean exitoInsumo = CRUD.insertarDB(sql, params);

    if (exitoInsumo) {
        try {
            String sqlIdInsumo = "SELECT LAST_INSERT_ID()";
            ResultSet rs = CRUD.consultarDB(sqlIdInsumo);
            if (rs != null && rs.next()) {
                int insumoId = rs.getInt(1);

                String sqlVacuna = "INSERT INTO Vacunas (ID, TipoEspecie, Lote, FechaIngreso, FechaVencimiento) VALUES (?, ?, ?, ?, ?)";
                Object[] paramsVacuna = {
                    insumoId,
                    vacuna.getTipoEspecie(),
                    vacuna.getLote(),
                    vacuna.getFechaIngreso(),
                    vacuna.getFechaVencimiento()
                };

                return CRUD.insertarDB(sqlVacuna, paramsVacuna);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return false;
}
    
    public static ArrayList<CategoriaInsumos> obtenerCategorias() {
    ArrayList<CategoriaInsumos> categorias = new ArrayList<>();
    String sql = "SELECT * FROM Categorias_Insumos";
    try (ResultSet rs = CRUD.consultarDB(sql)) {
        while (rs.next()) {
            int id = rs.getInt("ID");
            String nombre = rs.getString("Nombre");
            categorias.add(new CategoriaInsumos(id, nombre));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return categorias;
}

public static CategoriaInsumos obtenerCategoriaPorId(int id) {
    String sql = "SELECT * FROM Categorias_Insumos WHERE ID = ?";
    try (ResultSet rs = CRUD.consultarDB(sql, id)) {
        if (rs.next()) {
            String nombre = rs.getString("Nombre");
            return new CategoriaInsumos(id, nombre);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public static Proveedor obtenerProveedorPorId(int id) {
    String sql = "SELECT * FROM Proveedores WHERE ID = ?";
    Proveedor proveedor = null;

    try (ResultSet rs = CRUD.consultarDB(sql, id)) {
        if (rs != null && rs.next()) {
            String nombre = rs.getString("Nombre");
            String telefono = rs.getString("Telefono");
            String email = rs.getString("Email");
            String direccion = rs.getString("Direccion");
            String tipoProveedorString = rs.getString("Tipo_Proveedor");

            TipoProveedor tipoProveedor = TipoProveedor.valueOf(tipoProveedorString);

            proveedor = new Proveedor(id, nombre, telefono, email, direccion, tipoProveedor);
        }
    } catch (SQLException e) {
        Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al obtener proveedor por ID", e);
    }

    return proveedor;
}

public static boolean registrarMaterialMedico(MaterialMedico materialMedico) {
    CRUD.setConexion(ConnectionDB.getConnection());

    String sql = "INSERT INTO Insumos (Nombre, Fabricante, UnidadMedida, CategoriaID, NivelMinimo, Stock, PrecioUnitario, ID_Proveedor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    Object[] params = {
        materialMedico.getNombre(),
        materialMedico.getFabricante(),
        materialMedico.getUnidadMedida(),
        materialMedico.getCategoria().getId(),
        materialMedico.getNivelMinimo(),
        materialMedico.getCantidadStock(),
        materialMedico.getPrecioUnitario(),
        materialMedico.getProveedor().getId()
    };

    boolean exitoInsumo = CRUD.insertarDB(sql, params);

    if (exitoInsumo) {
        try {
            String sqlIdInsumo = "SELECT LAST_INSERT_ID()";
            ResultSet rs = CRUD.consultarDB(sqlIdInsumo);
            if (rs != null && rs.next()) {
                int insumoId = rs.getInt(1); 

                String sqlMaterialMedico = "INSERT INTO Material_Medico (ID, Tipo) VALUES (?, ?)";
                Object[] paramsMaterialMedico = {
                    insumoId,                      
                    materialMedico.getTipo(),           
                };

                
                return CRUD.insertarDB(sqlMaterialMedico, paramsMaterialMedico);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al registrar el material médico", ex);
        }
    }

    return false;
}

public static ArrayList<Medicamento> obtenerAlertasDeVencimientoMedicamentos(int diasAntes) {
    ArrayList<Medicamento> alertas = new ArrayList<>();
    CRUD.setConexion(ConnectionDB.getConnection());

    LocalDate hoy = LocalDate.now();
    LocalDate limite = hoy.plusDays(diasAntes);

    String sql = "SELECT m.ID, i.Nombre, i.Fabricante, i.UnidadMedida, i.CategoriaID, m.Tipo, m.TipoEspecie, m.FechaVencimiento, i.NivelMinimo, i.Stock, i.PrecioUnitario, i.ID_Proveedor " +
                 "FROM Medicamentos m " +
                 "INNER JOIN Insumos i ON m.ID = i.ID " +
                 "WHERE m.FechaVencimiento BETWEEN ? AND ?";

    try (ResultSet rs = CRUD.consultarDB(sql, java.sql.Date.valueOf(hoy), java.sql.Date.valueOf(limite))) {
        while (rs != null && rs.next()) {
            
            CategoriaInsumos categoria = obtenerCategoriaPorId(rs.getInt("CategoriaID"));
            Proveedor proveedor = obtenerProveedorPorId(rs.getInt("ID_Proveedor"));
            Medicamento medicamento = new Medicamento(
                rs.getString("Tipo"),
                rs.getString("TipoEspecie"),
                rs.getDate("FechaVencimiento").toLocalDate(),
                rs.getInt("ID"),
                rs.getString("Nombre"),
                rs.getString("Fabricante"),
                rs.getString("UnidadMedida"),
                categoria,
                rs.getInt("NivelMinimo"),
                rs.getInt("Stock"),
                rs.getBigDecimal("PrecioUnitario"),
                proveedor    

            );
            alertas.add(medicamento);
        }
    } catch (SQLException e) {
        Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al consultar alertas de vencimiento de medicamentos", e);
    } finally {
        CRUD.closeConexion(CRUD.getConexion());
    }

    return alertas;
}
public static ArrayList<Vacuna> obtenerAlertasDeVencimientoVacunas(int diasAntes) {
    ArrayList<Vacuna> alertas = new ArrayList<>();
    CRUD.setConexion(ConnectionDB.getConnection());

    LocalDate hoy = LocalDate.now();
    LocalDate limite = hoy.plusDays(diasAntes);

    String sql = "SELECT v.ID, i.Nombre, i.Fabricante, i.UnidadMedida, i.CategoriaID, v.TipoEspecie, v.FechaIngreso, v.FechaVencimiento, v.Lote, i.NivelMinimo, i.Stock, i.PrecioUnitario, i.ID_Proveedor " +
                 "FROM Vacunas v " +
                 "INNER JOIN Insumos i ON v.ID = i.ID " +
                 "WHERE v.FechaVencimiento BETWEEN ? AND ?";

    try (ResultSet rs = CRUD.consultarDB(sql, java.sql.Date.valueOf(hoy), java.sql.Date.valueOf(limite))) {
        while (rs != null && rs.next()) {
            CategoriaInsumos categoria = obtenerCategoriaPorId(rs.getInt("CategoriaID"));
            Proveedor proveedor = obtenerProveedorPorId(rs.getInt("ID_Proveedor"));
            Vacuna vacuna = new Vacuna(
                rs.getString("TipoEspecie"),
                rs.getString("Lote"),
                rs.getDate("FechaIngreso").toLocalDate(),
                rs.getDate("FechaVencimiento").toLocalDate(),
                rs.getInt("ID"),
                rs.getString("Nombre"),
                rs.getString("Fabricante"),
                rs.getString("UnidadMedida"),
                categoria,
                rs.getInt("NivelMinimo"),
                rs.getInt("Stock"),
                rs.getBigDecimal("PrecioUnitario"),
                proveedor
            );

            alertas.add(vacuna);
        }
    } catch (SQLException e) {
        Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al consultar alertas de vencimiento de vacunas", e);
    } finally {
        CRUD.closeConexion(CRUD.getConexion());
    }

    return alertas;
}

public static ArrayList<Insumo> obtenerProductosParaReabastecer() {
    ArrayList<Insumo> productosParaReabastecer = new ArrayList<>();
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT * FROM Insumos";
    
    try (ResultSet rs = CRUD.consultarDB(sql)) {
        while (rs.next()) {
            int cantidadStock = rs.getInt("Stock");
            int nivelMinimo = rs.getInt("NivelMinimo");
            BigDecimal precioUnitario = rs.getBigDecimal("PrecioUnitario");
            Proveedor proveedor = obtenerProveedorPorId(rs.getInt("ID_Proveedor"));
            if (cantidadStock < nivelMinimo) {
                Insumo insumo = new Insumo(
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    rs.getString("Fabricante"),
                    rs.getString("UnidadMedida"),
                    obtenerCategoriaPorId(rs.getInt("CategoriaID")),
                    nivelMinimo,
                    cantidadStock,
                    precioUnitario,
                    proveedor
                );
                productosParaReabastecer.add(insumo);
            }
        }
    } catch (SQLException e) {
        Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al obtener productos para reabastecer", e);
    } finally {
        CRUD.closeConexion(CRUD.getConexion());
    }
    
    return productosParaReabastecer;
}

public static BigDecimal obtenerPrecioUnitario(int insumoID) {
        String sql = "SELECT PrecioUnitario FROM Insumos WHERE ID = ?";
        BigDecimal precioUnitario = BigDecimal.ZERO;

        try (ResultSet rs = CRUD.consultarDB(sql, insumoID)) {
            if (rs != null && rs.next()) {
                precioUnitario = rs.getBigDecimal("PrecioUnitario");
            }
        } catch (SQLException e) {
            Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al obtener precio unitario", e);
        }

        return precioUnitario;
    }

    public static ArrayList<Proveedor> obtenerProveedores() {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedores";

        try (ResultSet rs = CRUD.consultarDB(sql)) {
            while (rs != null && rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("Nombre");
                String telefono = rs.getString("Telefono");
                String email = rs.getString("Email");
                String direccion = rs.getString("Direccion");
                String tipoProveedorString = rs.getString("Tipo_Proveedor");

                TipoProveedor tipoProveedor = TipoProveedor.valueOf(tipoProveedorString);

                Proveedor proveedor = new Proveedor(id, nombre, telefono, email, direccion, tipoProveedor);
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al obtener proveedores", e);
        }

        return proveedores;
    }

    public static void generarOrdenDeCompraAutomatica(ArrayList<Insumo> productosParaReabastecer) {
    CRUD.setConexion(ConnectionDB.getConnection());
    Scanner scanner = new Scanner(System.in);

    int proveedorID = obtenerProveedorParaInsumo(productosParaReabastecer.get(0).getId());
    LocalDate fechaOrden = LocalDate.now();
    BigDecimal totalOrden = BigDecimal.ZERO;

    String sqlOrden = "INSERT INTO OrdenesCompra (ID_Proveedor, Fecha, Total) VALUES (?, ?, ?)";

    if (CRUD.insertarDB(sqlOrden, proveedorID, java.sql.Date.valueOf(fechaOrden), totalOrden)) {
        String sqlUltimaOrden = "SELECT LAST_INSERT_ID()";
        try (ResultSet rs = CRUD.consultarDB(sqlUltimaOrden)) {
            if (rs != null && rs.next()) {
                int ordenId = rs.getInt(1);

                for (Insumo insumo : productosParaReabastecer) {
                    System.out.println("Producto: " + insumo.getNombre());
                    System.out.println("Nivel Mínimo: " + insumo.getNivelMinimo() + 
                                       ", Stock Actual: " + insumo.getCantidadStock());

                    int cantidadRequerida;
                    do {
                        System.out.print("Ingrese la cantidad a pedir (debe ser mayor que el nivel mínimo): ");
                        cantidadRequerida = scanner.nextInt();
                        if (cantidadRequerida <= insumo.getNivelMinimo()) {
                            System.out.println("La cantidad debe ser mayor al nivel mínimo. Intente de nuevo.");
                        }
                    } while (cantidadRequerida <= insumo.getNivelMinimo());

                    BigDecimal precioUnitario = obtenerPrecioUnitario(insumo.getId());
                    BigDecimal totalProducto = precioUnitario.multiply(BigDecimal.valueOf(cantidadRequerida));
                    totalOrden = totalOrden.add(totalProducto);

                    String sqlDetalle = "INSERT INTO DetalleOrdenCompra (ID_OrdenCompra, ID_Insumo, Cantidad, PrecioUnitario) VALUES (?, ?, ?, ?)";
                    CRUD.insertarDB(sqlDetalle, ordenId, insumo.getId(), cantidadRequerida, precioUnitario);
                }

                String sqlUpdateTotal = "UPDATE OrdenesCompra SET Total = ? WHERE ID = ?";
                CRUD.updateDB(sqlUpdateTotal, totalOrden, ordenId);

                System.out.println("Orden de compra generada exitosamente.");
            }
        } catch (SQLException e) {
            Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al obtener el último ID de la orden de compra", e);
        }
    } else {
        System.out.println("Hubo un problema al generar la orden de compra.");
    }
}

    public static int obtenerProveedorParaInsumo(int insumoID) {
        String sql = "SELECT ID_Proveedor FROM Insumos WHERE ID = ?";
        int proveedorID = -1;

        try (ResultSet rs = CRUD.consultarDB(sql, insumoID)) {
            if (rs != null && rs.next()) {
                proveedorID = rs.getInt("ID_Proveedor");
            }
        } catch (SQLException e) {
            Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al obtener proveedor para insumo", e);
        }

        return proveedorID;
    }
    
    public static int obtenerUltimaOrdenGenerada() {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT ID FROM OrdenesCompra ORDER BY ID DESC LIMIT 1";

    try (ResultSet rs = CRUD.consultarDB(sql)) {
        if (rs != null && rs.next()) {
            return rs.getInt("ID");
        }
    } catch (SQLException e) {
        Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al recuperar la última orden generada", e);
    }

    return -1; 
}
    
    public static void actualizarStock(int ordenId) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sqlDetallesOrden = "SELECT ID_Insumo, Cantidad FROM DetalleOrdenCompra WHERE ID_OrdenCompra = ?";
    String sqlActualizarStock = "UPDATE Insumos SET Stock = Stock + ? WHERE ID = ?";

    try (ResultSet rs = CRUD.consultarDB(sqlDetallesOrden, ordenId)) {
        if (rs != null) {
            while (rs.next()) {
                int insumoId = rs.getInt("ID_Insumo");
                int cantidad = rs.getInt("Cantidad");

                if (CRUD.updateDB(sqlActualizarStock, cantidad, insumoId)) {
                    System.out.println("Stock actualizado para el insumo ID: " + insumoId);
                } else {
                    System.out.println("Error al actualizar el stock para el insumo ID: " + insumoId);
                }
            }
            System.out.println("Actualización de stock completada.");
        } else {
            System.out.println("No se encontraron detalles para la orden de compra con ID: " + ordenId);
        }
    } catch (SQLException e) {
        Logger.getLogger(InsumosController.class.getName()).log(Level.SEVERE, "Error al actualizar el stock", e);
    }
}

}
