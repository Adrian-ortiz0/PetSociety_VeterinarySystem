
package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.Owner;
import com.mycompany.petsociety.models.Servicio;
import com.mycompany.petsociety.models.TipoServicio;
import com.mycompany.petsociety.models.TipoServicioAdicional;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiciosController {
    
    public static ArrayList<TipoServicioAdicional> mostrarServiciosAdicionales(){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "SELECT * FROM TiposServicioAdicional";
        ArrayList<TipoServicioAdicional> serviciosAdicionales = new ArrayList();
        try {
            ResultSet rs = CRUD.consultarDB(sql);
            while(rs != null && rs.next()){
                int id = rs.getInt("ID");
                String servicioEspecial = rs.getString("Nombre");
                double costoBase = rs.getDouble("CostoBase");
                serviciosAdicionales.add(new TipoServicioAdicional(id, servicioEspecial, costoBase));
            }
            if(serviciosAdicionales.isEmpty()){
                System.out.println("No hay servicios para mostrar");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiciosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serviciosAdicionales;
    }
    
    public static boolean registrarAdopcion(Owner owner, Animal animal){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "INSERT INTO Adopciones (ID_Mascota, ID_PropietarioNuevo, Fecha) VALUES (?, ? ,?)";
        Object[] params = {
            animal.getId(),
            owner.getId(),
            LocalDate.now()
        };
        return CRUD.insertarDB(sql, params);
    }
    
    public static ArrayList<Servicio> mostrarServiciosPorTipo(String tipoServicio) {
    String sql = "SELECT ID, Nombre, TipoServicio, CostoBase FROM Servicios WHERE TipoServicio = ?";
    ArrayList<Servicio> servicios = new ArrayList<>();

    try (ResultSet rs = CRUD.consultarDB(sql, tipoServicio)) {
        while (rs != null && rs.next()) {
            int id = rs.getInt("ID");
            String nombre = rs.getString("Nombre");
            String tipoServicioStr = rs.getString("TipoServicio");
            BigDecimal costoBase = rs.getBigDecimal("CostoBase");
            
            TipoServicio tipo = TipoServicio.valueOf(tipoServicioStr);
            
            servicios.add(new Servicio(id, tipo, costoBase, nombre));
        }

        if (servicios.isEmpty()) {
            System.out.println("No hay servicios disponibles para este tipo.");
        }

    } catch (SQLException ex) {
        System.out.println("Error al consultar los servicios: " + ex.getMessage());
    }

    return servicios;
}
    public static boolean insertarServicioConHistorial(int servicioID, int mascotaID, int veterinarioID, LocalDate fechaServicio, BigDecimal costoFinal, Owner owner, String descripcion) {
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
                        boolean detallesInsertados = CRUD.insertarDB(sqlDetallesFactura, facturaID, servicioID, descripcion, 1, costoFinal);
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
}
