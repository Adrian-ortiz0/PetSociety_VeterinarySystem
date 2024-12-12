package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.Proveedor;
import com.mycompany.petsociety.models.TipoProveedor;
import com.mycompany.petsociety.persistence.CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProveedorController {
    public static ArrayList<Proveedor> obtenerTodosProveedores() {
    ArrayList<Proveedor> proveedores = new ArrayList<>();
    String sql = "SELECT * FROM Proveedores";
    
    try (ResultSet rs = CRUD.consultarDB(sql)) {
        while (rs.next()) {
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
        Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, "Error al obtener proveedores", e);
    }
    
    return proveedores;
}
    public static Proveedor obtenerProveedorPorId(int proveedorId) {
        Proveedor proveedor = null;
        String sql = "SELECT * FROM Proveedores WHERE ID = ?";

        ResultSet rs = CRUD.consultarDB(sql, proveedorId);

        try {
    if (rs != null && rs.next()) {
        int id = rs.getInt("ID");
        String nombre = rs.getString("Nombre");
        String telefono = rs.getString("Telefono");
        String email = rs.getString("Email");
        String direccion = rs.getString("Direccion");

        String tipoProveedorStr = rs.getString("Tipo_Proveedor");

        TipoProveedor tipoProveedor = TipoProveedor.valueOf(tipoProveedorStr.toUpperCase());

        proveedor = new Proveedor(id, nombre, telefono, email, direccion, tipoProveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedor;
    }
}
