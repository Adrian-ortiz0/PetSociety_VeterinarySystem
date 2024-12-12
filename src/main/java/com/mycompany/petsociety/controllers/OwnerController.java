package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.Owner;
import com.mycompany.petsociety.models.PetHistory;
import com.mycompany.petsociety.models.PetStatus;
import com.mycompany.petsociety.models.Sex;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OwnerController {
    
    public static Owner iniciarSesion(String email, String cedula) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT * FROM Propietarios WHERE Email = ? AND Cedula = ?";
    Object[] params = {email, cedula};

    try {
        ResultSet rs = CRUD.consultarDB(sql, params);
        if (rs != null && rs.next()) {
            int ownerId = rs.getInt("ID");
            String nombre = rs.getString("Nombre");
            String cedulaUsuario = rs.getString("Cedula");
            String address = rs.getString("Direccion");
            String telefono = rs.getString("Telefono");
            String emailUsuario = rs.getString("Email");
            String contactoEmergencia = rs.getString("ContactoEmergencia");
            LocalDateTime fechaRegistro = rs.getTimestamp("FechaRegistro").toLocalDateTime();

            return new Owner(ownerId, nombre, cedulaUsuario, address, telefono, emailUsuario, contactoEmergencia, fechaRegistro);
        }
    } catch (SQLException ex) {
        Logger.getLogger(OwnerController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}
    public static boolean darMascotaEnAdopcion(Animal pet){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "UPDATE Mascotas SET Estatus = 'ESPERANDO_ADOPCION', ID_Propietario = NULL WHERE ID = ?";
        return CRUD.updateDB(sql, pet.getId());
    }
    
    public static boolean adoptarMascota(Owner owner){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "UPDATE Mascotas SET Estatus = 'ADOPTADA', ID_Propietario = ?";
        return CRUD.updateDB(sql, owner.getId());
    }
    
public static ArrayList<PetHistory> verHistorialMedidasMascota(Animal pet) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT Mascotas.Nombre AS NombreMascota, " +
                 "HistorialMedidas.Peso, " +
                 "HistorialMedidas.Altura, " +
                 "HistorialMedidas.FechaRegistro " +
                 "FROM HistorialMedidas " +
                 "JOIN Mascotas ON HistorialMedidas.ID_Mascota = Mascotas.ID " +
                 "WHERE Mascotas.ID = ?";
    
    Object[] params = {pet.getId()};
    
    ArrayList<PetHistory> historial = new ArrayList<>();
    
    try {
        ResultSet rs = CRUD.consultarDB(sql, params);
        
        while (rs != null && rs.next()) {
            
            PetHistory petHistory = new PetHistory();
            
            petHistory.setWeight(rs.getDouble("Peso"));
            petHistory.setHeight(rs.getDouble("Altura"));
            petHistory.setRegisterDate(rs.getTimestamp("FechaRegistro").toLocalDateTime());
            petHistory.setPet(pet);

            historial.add(petHistory);
        }
    } catch (SQLException ex) {
        Logger.getLogger(OwnerController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return historial;
}
    
    public static ArrayList<Animal> verMascotas(Owner owner) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT m.ID, m.Nombre, m.Especie, m.Raza, m.FechaNacimiento, " +
                 "m.Sexo, m.Microchip, m.Foto, m.Estatus, m.FechaRegistro " +
                 "FROM Propietarios p " +
                 "JOIN Mascotas m ON p.ID = m.ID_Propietario " +
                 "WHERE p.ID = ?";
    Object[] params = {owner.getId()};
    ArrayList<Animal> mascotas = new ArrayList<>();
    
    try {
        ResultSet rs = CRUD.consultarDB(sql, params);
        while (rs != null && rs.next()) {
            Animal animal = new Animal();
            animal.setId(rs.getInt("ID"));
            animal.setName(rs.getString("Nombre"));
            animal.setSpecie(rs.getString("Especie"));
            animal.setBreed(rs.getString("Raza"));
            animal.setBirthDate(rs.getDate("FechaNacimiento").toLocalDate());
            animal.setSex(Sex.valueOf(rs.getString("Sexo")));
            animal.setMicroChip(rs.getString("Microchip"));
            animal.setPhoto(rs.getString("Foto"));
            animal.setPetStatus(PetStatus.valueOf(rs.getString("Estatus")));
            animal.setRegisterDate(rs.getTimestamp("FechaRegistro").toLocalDateTime());
            animal.setOwner(owner);
            
            mascotas.add(animal);
        }
    } catch (SQLException ex) {
        Logger.getLogger(OwnerController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return mascotas;
}
    public static ArrayList<Animal> obtenerMascotasDePropietario(Owner owner) {
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "SELECT m.ID, m.Nombre, m.Especie, m.Raza, m.FechaNacimiento, "
                   + "m.Sexo, m.Microchip, m.Foto, m.Estatus, m.FechaRegistro "
                   + "FROM Mascotas m "
                   + "WHERE m.ID_Propietario = ?";
        Object[] params = {owner.getId()};

        ArrayList<Animal> mascotas = new ArrayList<>();
        
        try {
            ResultSet rs = CRUD.consultarDB(sql, params);
            while (rs != null && rs.next()) {
                Animal animal = new Animal();
                animal.setId(rs.getInt("ID"));
                animal.setName(rs.getString("Nombre"));
                animal.setSpecie(rs.getString("Especie"));
                animal.setBreed(rs.getString("Raza"));
                animal.setBirthDate(rs.getDate("FechaNacimiento").toLocalDate());
                animal.setSex(Sex.valueOf(rs.getString("Sexo")));
                animal.setMicroChip(rs.getString("Microchip"));
                animal.setPhoto(rs.getString("Foto"));
                animal.setPetStatus(PetStatus.valueOf(rs.getString("Estatus")));
                animal.setRegisterDate(rs.getTimestamp("FechaRegistro").toLocalDateTime());
                animal.setOwner(owner);
                
                mascotas.add(animal);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OwnerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mascotas;
    }
    
    public static Owner obtenerPropietarioPorId(int idPropietario) {
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "SELECT * FROM Propietarios WHERE ID = ?";
        ResultSet resultSet = null;
        Owner owner = null;

        try {
            resultSet = CRUD.consultarDB(sql, idPropietario);
            if (resultSet != null && resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nombre = resultSet.getString("Nombre");
                String cedula = resultSet.getString("Cedula");
                String direccion = resultSet.getString("Direccion");
                String telefono = resultSet.getString("Telefono");
                String email = resultSet.getString("Email");
                String contactoEmergencia = resultSet.getString("ContactoEmergencia");
                LocalDateTime fechaRegistro = resultSet.getTimestamp("FechaRegistro").toLocalDateTime();

                owner = new Owner(id, nombre, cedula, telefono, email, direccion, contactoEmergencia, fechaRegistro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return owner;
    }
    public static Owner obtenerPropietarioPorCedula(String cedula) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT * FROM Propietarios WHERE Cedula = ?";
    ResultSet resultSet = null;
    Owner owner = null;

    try {
        resultSet = CRUD.consultarDB(sql, cedula);
        if (resultSet != null && resultSet.next()) {
            int id = resultSet.getInt("ID");
            String nombre = resultSet.getString("Nombre");
            String direccion = resultSet.getString("Direccion");
            String telefono = resultSet.getString("Telefono");
            String email = resultSet.getString("Email");
            String contactoEmergencia = resultSet.getString("ContactoEmergencia");
            LocalDateTime fechaRegistro = resultSet.getTimestamp("FechaRegistro").toLocalDateTime();

            owner = new Owner(id, nombre, cedula, telefono, email, direccion, contactoEmergencia, fechaRegistro);
        }
    } catch (SQLException ex) {
        Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    return owner;
}

}
