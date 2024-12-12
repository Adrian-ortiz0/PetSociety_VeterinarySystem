package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.Admin;
import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.CategoriaInsumos;
import com.mycompany.petsociety.models.Owner;
import com.mycompany.petsociety.models.Veterinarian;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminController {
    
    public static Admin iniciarSesionAdmin(String correo, String documento) {
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "SELECT * FROM Administrator WHERE Email = ? AND Cedula = ?";
        Object[] params = {correo, documento};
        try {
            ResultSet rs = CRUD.consultarDB(sql, params);
            if (rs != null && rs.next()) {
                String nombre = rs.getString("Nombre");
                String cedula = rs.getString("Cedula");
                String telefono = rs.getString("Telefono");
                String email = rs.getString("Email");
                return new Admin(nombre, cedula, telefono, email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    public static Owner obtenerOwnerPorCedula(String cedula) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT * FROM Propietarios WHERE Cedula = ?";
    Object[] params = {cedula};
    
    try {
        ResultSet rs = CRUD.consultarDB(sql, params);
        if (rs != null && rs.next()) {
            int id = rs.getInt("ID");
            String nombre = rs.getString("Nombre");
            String telefono = rs.getString("Telefono");
            String email = rs.getString("Email");
            String direccion = rs.getString("Direccion");
            String contactoEmergencia = rs.getString("ContactoEmergencia");
            LocalDateTime fechaRegistro = rs.getTimestamp("FechaRegistro").toLocalDateTime();
            
            return new Owner(id, nombre, cedula, telefono, email, direccion, contactoEmergencia, fechaRegistro);
        }
    } catch (SQLException ex) {
        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}
    
    public static boolean actualizarDireccionOwner(Owner owner, String address) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "UPDATE Propietarios SET Direccion = ? WHERE Cedula = ?";
    Object [] params = {
        address,
        owner.getCedula()
    };
    return CRUD.updateDB(sql, params);
}
    public static boolean actualizarTelefonoOwner(Owner owner, String phone) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "UPDATE Propietarios SET Telefono = ? WHERE Cedula = ?";
    Object [] params = {
        phone,
        owner.getCedula()
    };
    return CRUD.updateDB(sql, params);
}
    public static boolean actualizarEmailOwner(Owner owner, String email) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "UPDATE Propietarios SET Email = ? WHERE Cedula = ?";
    Object [] params = {
        email,
        owner.getCedula()
    };
    return CRUD.updateDB(sql, params);
}
    public static boolean eliminarPropietarioPorCedula( String cedula){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "DELETE FROM Propietarios WHERE Cedula = ?";
        
        Object[] params = {
            cedula
        };
        return CRUD.eliminarDB(sql, params);
    }
    
    public static ArrayList<String> obtenerPropietariosConMascotas() {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT p.Nombre AS Propietario, m.Nombre AS Mascota " +
                 "FROM Propietarios p " +
                 "JOIN Mascotas m ON p.ID = m.ID_Propietario";
    
    ArrayList<String> resultado = new ArrayList<>();
    
    try {
        ResultSet rs = CRUD.consultarDB(sql, new Object[0]);
        while (rs != null && rs.next()) {
            String propietario = rs.getString("Propietario");
            String mascota = rs.getString("Mascota");
            resultado.add("Propietario: " + propietario + ", Mascota: " + mascota);
        }
    } catch (SQLException ex) {
        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return resultado;
}
    
    
    public static boolean insertarVeterinarios(Veterinarian vet){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "INSERT INTO Veterinarios (Cedula, Nombre, Licencia, Especialidad, Telefono, Email, Contratado)VALUES (?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {
            vet.getCedula(),
            vet.getNombre(),
            vet.getLicencia(),
            vet.getEspecialidad(),
            vet.getTelefono(),
            vet.getEmail(),
            true
        };
        boolean result = CRUD.insertarDB(sql, params);
        if(result){
            System.out.println("Se contrato al veterinario: " + vet.getNombre());
        } else {
            System.out.println("Hubo un problema");
        }
        return result;
    }
    
    public static boolean despedirVeterinarios(String cedula){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "UPDATE Veterinarios SET Contratado = FALSE WHERE Cedula = ?;";
        Object[] params = {
            cedula
        };
        boolean result = CRUD.updateDB(sql, params);
        if(result){
            System.out.println("Se despidió al veterinario");
        } else {
            System.out.println("Error en el despido");
        }
        return result;
    }
    
    public static ArrayList<String> mostrarVeterinarios(){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "select v.Nombre as NombreVeterinario, v.Contratado as Estado from Veterinarios v;";
        ArrayList<String> resultado = new ArrayList();
        ResultSet rs = CRUD.consultarDB(sql, new Object[0]);
        try {
            while(rs != null && rs.next()){
                String nombreVet = rs.getString("NombreVeterinario");
                boolean estadoVet = rs.getBoolean("Estado");
                resultado.add("Nombre Veterinario: " + nombreVet + " Contratado: " + estadoVet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public static boolean insertarOwner(Owner owner) {
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "INSERT INTO Propietarios (Nombre, Cedula, Direccion, Telefono, Email, ContactoEmergencia, Estatus) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {
            owner.getName(),
            owner.getCedula(),
            owner.getAddress(),
            owner.getPhone(),
            owner.getEmail(),
            owner.getEmergencyContact(),
            true
        };

        boolean result = CRUD.insertarDB(sql, params);
        if (result) {
            System.out.println("Propietario insertado correctamente: " + owner.getName());
            String sqlId = "SELECT ID FROM Propietarios WHERE Cedula = ?";
            Object[] paramsId = {owner.getCedula()};
            try {
                ResultSet rs = CRUD.consultarDB(sqlId, paramsId);
                if (rs != null && rs.next()) {
                    owner.setId(rs.getInt("ID"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.err.println("Hubo un problema al insertar el propietario.");
        }
        return result;
    }
    
    public static boolean insertarAnimalesParaAdopcion(Animal animal){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "INSERT INTO Mascotas (Nombre, Especie, Raza, FechaNacimiento, Sexo, Microchip, Foto, Estatus) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        
        Object[] params = {
            animal.getName(),
            animal.getSpecie(),
            animal.getBreed(),
            animal.getBirthDate(),
            animal.getSex().getSex(),
            animal.getMicroChip(),
            animal.getPhoto(),
            animal.getPetStatus().getStatus(),
        };
        boolean result = CRUD.insertarDB(sql, params);
        if(result){
            System.out.println("Animal rescatado con exito, Bienvenido: " + animal.getName());
        } else {
            System.out.println("Hubo un fallo en el rescate / registro");
        }
        return result;
    }

    public static boolean insertarMascota(Animal animal, Owner owner) {
        CRUD.setConexion(ConnectionDB.getConnection());
        
        int ownerId = owner.getId();

        String sql = "INSERT INTO Mascotas (Nombre, Especie, Raza, FechaNacimiento, Sexo, Microchip, Foto, Estatus, ID_Propietario) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Object[] params = {
            animal.getName(),
            animal.getSpecie(),
            animal.getBreed(),
            animal.getBirthDate(),
            animal.getSex().getSex(),
            animal.getMicroChip(),
            animal.getPhoto(),
            animal.getPetStatus().getStatus(),
            ownerId
        };

        try {
            boolean result = CRUD.insertarDB(sql, params);
            if (result) {
                System.out.println("Mascota registrada correctamente: " + animal.getName());
            } else {
                System.out.println("Hubo un problema al registrar la mascota: " + animal.getName());
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error al registrar la mascota: " + e.getMessage());
            return false;
        }
    }
}