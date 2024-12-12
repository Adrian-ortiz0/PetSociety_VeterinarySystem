package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.Veterinarian;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeterinarianController {
    
    public static ArrayList<Veterinarian> obtenerTodosLosVeterinarios() {
    ArrayList<Veterinarian> veterinarios = new ArrayList<>();
    String sql = "SELECT * FROM Veterinarios";
    
    try {
        CRUD.setConexion(ConnectionDB.getConnection());
        ResultSet rs = CRUD.consultarDB(sql);
        
        while (rs.next()) {
        int id = rs.getInt("ID");
        String cedula = rs.getString("Cedula");
        String nombre = rs.getString("Nombre");
        String licencia = rs.getString("Licencia");
        String especialidad = rs.getString("Especialidad");
        String telefono = rs.getString("Telefono");
        String email = rs.getString("Email");
        boolean contratado = rs.getBoolean("Contratado");

        Veterinarian veterinarian = new Veterinarian(id, cedula, nombre, licencia, especialidad, telefono, email, contratado);
        veterinarios.add(veterinarian);
    }
    } catch (SQLException ex) {
        Logger.getLogger(VeterinarianController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return veterinarios;
}
}
