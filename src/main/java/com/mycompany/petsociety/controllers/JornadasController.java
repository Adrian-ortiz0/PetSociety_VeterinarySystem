package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.JornadasEspeciales;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JornadasController {
    
    public static boolean insertarJornadasRealizadas(JornadasEspeciales jornada){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "INSERT INTO JornadasEspeciales (Nombre, Fecha, Descripcion, Especie, DemandaEsperada, ImpactoJornada) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] params = {
            jornada.getNombre(),
            jornada.getFecha(),
            jornada.getDescripcion(),
            jornada.getTipo().name(),
            jornada.getDemandaEsperada(),
            jornada.getImpactoJornada()
        };
        return CRUD.insertarDB(sql, params);
    }
}
