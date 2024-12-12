package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.Owner;
import com.mycompany.petsociety.models.PetStatus;
import com.mycompany.petsociety.models.Sex;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetController {
    
    public static ArrayList<Animal> mostrarAnimalesConDueño() {
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<Animal> listaConDueno = new ArrayList<>();

    try {
        connection = ConnectionDB.getConnection();
        
        String sql = "SELECT * FROM Mascotas WHERE ID_Propietario IS NOT NULL";
        pstmt = connection.prepareStatement(sql);
        
        rs = pstmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("Nombre");
            String breed = rs.getString("Raza");
            LocalDate birthDate = rs.getDate("FechaNacimiento") != null ? rs.getDate("FechaNacimiento").toLocalDate() : null;
            Sex sex = Sex.valueOf(rs.getString("Sexo"));
            String microChip = rs.getString("Microchip");
            String photo = rs.getString("Foto");
            LocalDateTime registerDate = rs.getTimestamp("FechaRegistro").toLocalDateTime();
            PetStatus petStatus = PetStatus.valueOf(rs.getString("Estatus"));

            int ownerId = rs.getInt("ID_Propietario");
            Owner owner = OwnerController.obtenerPropietarioPorId(ownerId);

            Animal animal = new Animal(id, name, breed, birthDate, sex, microChip, photo, registerDate, petStatus, owner);
            listaConDueno.add(animal);
        }
    } catch (SQLException ex) {
        Logger.getLogger(PetController.class.getName()).log(Level.SEVERE, "Error retrieving pets", ex);
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PetController.class.getName()).log(Level.SEVERE, "Error closing ResultSet", ex);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(PetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    return listaConDueno;
}
    
    public static ArrayList<Animal> mostrarAnimalesParaAdoptar() {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT * FROM Mascotas WHERE Estatus = 'ESPERANDO_ADOPCION'";
    ArrayList<Animal> listaAdopcion = new ArrayList<>();

    try {
        ResultSet rs = CRUD.consultarDB(sql);
        while (rs != null && rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("Nombre");
            String breed = rs.getString("Raza");
            LocalDate birthDate = rs.getDate("FechaNacimiento") != null ? rs.getDate("FechaNacimiento").toLocalDate() : null;
            Sex sex = Sex.valueOf(rs.getString("Sexo"));
            String microChip = rs.getString("Microchip");
            String photo = rs.getString("Foto");
            LocalDateTime registerDate = rs.getTimestamp("FechaRegistro").toLocalDateTime();
            PetStatus petStatus = PetStatus.valueOf(rs.getString("Estatus"));

            Owner owner = null;

            Animal animal = new Animal(id, name, breed, birthDate, sex, microChip, photo, registerDate, petStatus, owner);
            listaAdopcion.add(animal);
        }
    } catch (SQLException ex) {
        Logger.getLogger(PetController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return listaAdopcion;
}
}
