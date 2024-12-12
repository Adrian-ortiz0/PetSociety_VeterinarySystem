
package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.CategoriaInsumos;
import com.mycompany.petsociety.models.Desparacitario;
import com.mycompany.petsociety.models.Insumo;
import com.mycompany.petsociety.models.Owner;
import com.mycompany.petsociety.models.Proveedor;
import com.mycompany.petsociety.models.TipoAntiParasitario;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DesparacitantesController {
    
    public static boolean insertarServicioConHistorial(int servicioID, int mascotaID, int veterinarioID, LocalDate fechaServicio, BigDecimal costoFinal, Owner owner) {
    java.sql.Date fecha = java.sql.Date.valueOf(fechaServicio);
    
    java.sql.Date proximaDosis = java.sql.Date.valueOf(fechaServicio.plusYears(1));
    
    String sqlHistorial = "INSERT INTO HistorialServicios (MascotaID, ServicioID, VeterinarioID, FechaAplicacion, ProximaDosis) " +
                          "VALUES (?, ?, ?, ?, ?)";
    
    try {
        boolean historialInsertado = CRUD.insertarDB(sqlHistorial, mascotaID, servicioID, veterinarioID, fecha, proximaDosis);
        
        if (historialInsertado) {
            String sqlFactura = "INSERT INTO Facturas (NombreEmpresa, NIT, Fecha, Direccion, Telefono, Email, Total, Cufe, ID_Propietario) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String cufe = "7328"; 
            BigDecimal totalFactura = costoFinal;
            boolean facturaInsertada = CRUD.insertarDB(sqlFactura, "PetSociety", "7328", LocalDate.now().toString(), 
                                                       "Cra20#110-69", "3173109599", "petsociety@gmail.com", totalFactura, 
                                                       cufe, owner.getId());
            
            if (facturaInsertada) {
                String sqlObtenerFacturaID = "SELECT LAST_INSERT_ID()";
                try (ResultSet rsFactura = CRUD.consultarDB(sqlObtenerFacturaID)) {
                    if (rsFactura.next()) {
                        int facturaID = rsFactura.getInt(1);

                        String sqlDetallesFactura = "INSERT INTO DetallesFactura (ID_Factura, ID_Servicio, Descripcion, Cantidad, PrecioUnitario) " +
                                                    "VALUES (?, ?, ?, ?, ?)";
                        boolean detallesInsertados = CRUD.insertarDB(sqlDetallesFactura, facturaID, servicioID, "Desparacitación", 1, costoFinal);
                        return detallesInsertados;
                    }
                }
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
}

    
    public static ArrayList<Desparacitario> mostrarDesparacitariosPorTipo(TipoAntiParasitario tipo) {
    CRUD.setConexion(ConnectionDB.getConnection());
    
    String sql = "SELECT d.ID, d.TipoAntiparasitario, d.TipoEspecie, d.FechaVencimiento, " +
                 "i.ID AS insumoID, i.Nombre, i.Fabricante, i.UnidadMedida, i.CategoriaID, " +
                 "i.NivelMinimo, i.Stock, i.PrecioUnitario, i.ID_Proveedor " +
                 "FROM Desparacitarios d " +
                 "JOIN Insumos i ON d.ID = i.ID " +
                 "WHERE d.TipoAntiparasitario = ?";
    
    String params = tipo.name();
    ArrayList<Desparacitario> desparacitariosList = new ArrayList<>();
    try {
        ResultSet rs = CRUD.consultarDB(sql, params);
        if (rs != null) {
            while (rs.next()) {
                int idDesparacitario = rs.getInt("ID");
                TipoAntiParasitario tipoAntiParasitario = TipoAntiParasitario.valueOf(rs.getString("TipoAntiparasitario"));
                String tipoEspecie = rs.getString("TipoEspecie");
                
                LocalDate fechaVencimiento = null;
                Date sqlFechaVencimiento = rs.getDate("FechaVencimiento");
                if (sqlFechaVencimiento != null) {
                    fechaVencimiento = sqlFechaVencimiento.toLocalDate();
                }
                
                int idInsumo = rs.getInt("insumoID");
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
                
                Desparacitario desparacitario = new Desparacitario(
                        idDesparacitario,
                        nombre,
                        fabricante,
                        unidadMedida,
                        categoria,
                        nivelMinimo,
                        cantidadStock,
                        precioUnitario,
                        proveedor,
                        tipoAntiParasitario,
                        tipoEspecie,
                        fechaVencimiento
                );
                desparacitariosList.add(desparacitario);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(DesparacitantesController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return desparacitariosList;
}
class FechaVencimientoNulaException extends RuntimeException {
    public FechaVencimientoNulaException(String message) {
        super(message);
    }
}
}
