package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.Cita;
import com.mycompany.petsociety.models.EstadoCita;
import com.mycompany.petsociety.models.Factura;
import com.mycompany.petsociety.models.Owner;
import com.mycompany.petsociety.models.PetStatus;
import com.mycompany.petsociety.models.Servicio;
import com.mycompany.petsociety.models.Sex;
import com.mycompany.petsociety.models.TipoServicio;
import com.mycompany.petsociety.models.TipoServicioAdicional;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class DateController {
    
    
    public static boolean actualizarCita(Cita cita){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "UPDATE Citas SET Estado = ? WHERE ID = ?";
        Object [] params = {cita.getEstado().name(), cita.getId()};
        return CRUD.updateDB(sql, params);
    }
    
    public static boolean insertarNuevasMedidas(Animal animal, double peso, double altura, LocalDate fechaRegistro){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "INSERT INTO HistorialMedidas(ID_Mascota, Peso, Altura, FechaRegistro) VALUES (?, ?, ?, ?)";
        Object[] params = {animal.getId(), peso, altura, fechaRegistro};
        return CRUD.insertarDB(sql, params);
    }
    
    public static boolean insertarCitas(Cita cita){
    CRUD.setConexion(ConnectionDB.getConnection());
    
    String sql = "INSERT INTO Citas (ID_Mascota, Fecha, Hora, Estado) VALUES (?, ?, ?, ?)";
    Object[] params = {cita.getAnimal().getId(), cita.getFecha(), cita.getHora(), cita.getEstado().name()};
    return CRUD.insertarDB(sql, params);
}
    public static ArrayList<Cita> obtenerCitas() {
    ArrayList<Cita> citas = new ArrayList<>();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    String sql = "SELECT " +
                 "Citas.ID, " +
                 "Citas.ID_Mascota, " +
                 "Citas.Fecha, " +
                 "Citas.Hora, " +
                 "Citas.Estado, " +
                 "Mascotas.Nombre, " +
                 "Mascotas.Raza, " +
                 "Mascotas.FechaNacimiento, " +
                 "Mascotas.Sexo, " +
                 "Mascotas.Microchip, " +
                 "Mascotas.Foto, " +
                 "Mascotas.Estatus, " +
                 "Mascotas.FechaRegistro, " +
                 "Mascotas.Especie, " +
                 "Mascotas.ID_Propietario " +
                 "FROM Citas " +
                 "JOIN Mascotas ON Citas.ID_Mascota = Mascotas.ID";

    try {
        connection = ConnectionDB.getConnection();
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            int idMascota = resultSet.getInt("ID_Mascota");
            LocalDate fecha = resultSet.getDate("Fecha").toLocalDate();
            LocalTime hora = resultSet.getTime("Hora") != null ? resultSet.getTime("Hora").toLocalTime() : null;
            EstadoCita estado = EstadoCita.valueOf(resultSet.getString("Estado"));

            String nombre = resultSet.getString("Nombre");
            String raza = resultSet.getString("Raza");
            LocalDate fechaNacimiento = resultSet.getDate("FechaNacimiento") != null
                                            ? resultSet.getDate("FechaNacimiento").toLocalDate()
                                            : null;
            Sex sexo = resultSet.getString("Sexo") != null ? Sex.valueOf(resultSet.getString("Sexo")) : null;
            String microchip = resultSet.getString("Microchip");
            String foto = resultSet.getString("Foto");
            LocalDateTime fechaRegistro = resultSet.getTimestamp("FechaRegistro") != null
                                              ? resultSet.getTimestamp("FechaRegistro").toLocalDateTime()
                                              : null;
            PetStatus petStatus = resultSet.getString("Estatus") != null
                                  ? PetStatus.valueOf(resultSet.getString("Estatus"))
                                  : null;
            String especie = resultSet.getString("Especie");
            Owner owner = OwnerController.obtenerPropietarioPorId(resultSet.getInt("ID_Propietario"));

            Animal animal = new Animal(
                idMascota, 
                nombre, 
                raza, 
                fechaNacimiento, 
                sexo, 
                microchip, 
                foto, 
                fechaRegistro, 
                petStatus, 
                owner
            );
            animal.setSpecie(especie);

            Cita cita = new Cita(id, animal, fecha, hora, estado);

            citas.add(cita);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Cerrar recursos correctamente
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    return citas;
}
}
