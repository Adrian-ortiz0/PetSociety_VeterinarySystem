package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.CategoriaInsumos;
import com.mycompany.petsociety.persistence.CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaController {
    public static CategoriaInsumos obtenerCategoriaPorId(int categoriaId) {
    String sql = "SELECT * FROM Categorias_Insumos WHERE ID = ?";

    ResultSet rs = CRUD.consultarDB(sql, categoriaId);
    CategoriaInsumos categoriaInsumos = null;

    try {
        if (rs != null && rs.next()) {
            int id = rs.getInt("ID");
            String nombre = rs.getString("Nombre");

            categoriaInsumos = new CategoriaInsumos(id, nombre);
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    }

    return categoriaInsumos;
}
}
