
package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.ClubMember;
import com.mycompany.petsociety.models.DescuentosStrategy;
import com.mycompany.petsociety.models.DescuentosStrategyFactory;
import com.mycompany.petsociety.models.Owner;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClubController {
    
    public static boolean sumarPuntosPorConsulta(ClubMember member){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "UPDATE ClubMascotasFrecuentes SET PuntosAcumulados = PuntosAcumulados + 10 WHERE ID_Propietario = ?";
        Object[] params = {member.getId()};
        return CRUD.updateDB(sql, params);
        
    }
    
    public static boolean insertarMiembrosEnClub(Owner owner){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "INSERT INTO ClubMascotasFrecuentes (ID_Propietario) VALUES (?)";
        Object[] params = {owner.getId()};
        return CRUD.insertarDB(sql, params);
    }
    
    public static ClubMember obtenerMiembrosPorId(Owner owner) {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT * FROM ClubMascotasFrecuentes WHERE ID_Propietario = ?;";
    Object[] params = {owner.getId()};
    
    try (ResultSet rs = CRUD.consultarDB(sql, params)) {
        if (rs != null && rs.next()) {
            int id = rs.getInt("ID");
            String nivel = rs.getString("Nivel");
            int accumulatedPoints = rs.getInt("PuntosAcumulados");
            
            DescuentosStrategy discountStrategy = DescuentosStrategyFactory.obtenerEstrategiaPorPuntos(accumulatedPoints);
            
            return new ClubMember(id, owner, nivel, accumulatedPoints, discountStrategy);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ClubController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return null;
}
}
