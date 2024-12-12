package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.CategoriaInsumos;
import com.mycompany.petsociety.models.Proveedor;
import com.mycompany.petsociety.models.TipoAnimalInsumo;
import com.mycompany.petsociety.models.Vacuna;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VacunasController {
    
    public static boolean registrarAplicacionVacuna(
    int mascotaId,
    int vacunaId,
    int veterinarioId,
    LocalDate fechaAplicacion, 
    LocalDate proximaDosis
) {
    String sql = "INSERT INTO HistorialVacunacion (ID_Mascota, ID_Vacuna, ID_Veterinario, FechaAplicacion, ProximaDosis) " +
                 "VALUES (?, ?, ?, ?, ?)";
    CRUD.setConexion(ConnectionDB.getConnection());
    Object[] params = {mascotaId, vacunaId, veterinarioId,
        Date.valueOf(fechaAplicacion),
        Date.valueOf(proximaDosis)};
    return CRUD.insertarDB(sql, params);
}
    
    public static boolean restarUnidadVacuna(int vacunaId) {
    String sql = "UPDATE Insumos SET Stock = Stock - 1 WHERE ID = ?";
    
    CRUD.setConexion(ConnectionDB.getConnection());
    Object[] params = {vacunaId};
    return CRUD.updateDB(sql, params);
}
    
    public static boolean restarCantidadInsumos(int insumoId, int cantidad) {
    String sqlVerificarStock = "SELECT Stock FROM Insumos WHERE ID = ?";
    CRUD.setConexion(ConnectionDB.getConnection());
    Object[] paramsVerificar = {insumoId};
    
    ResultSet rs = CRUD.consultarDB(sqlVerificarStock, paramsVerificar);
    int stockActual = 0;

    try {
        if (rs != null && rs.next()) {
            stockActual = rs.getInt("Stock");
        }
    } catch (SQLException ex) {
        Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    if (stockActual < cantidad) {
        System.out.println("No hay suficiente stock disponible.");
        return false;
    }

    String sqlRestarStock = "UPDATE Insumos SET Stock = Stock - ? WHERE ID = ?";
    Object[] paramsRestar = {cantidad, insumoId};
    
    return CRUD.updateDB(sqlRestarStock, paramsRestar);
}
    
    public static ArrayList<Vacuna> mostrarTodasLasVacunasDeMascota(Animal animal){
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT " +
                    "Insumos.ID AS InsumoID, " +
                    "Vacunas.TipoEspecie, " +
                    "Vacunas.Lote, " +
                    "Vacunas.FechaIngreso, " +
                    "Vacunas.FechaVencimiento, " +
                    "HistorialVacunacion.FechaAplicacion, " +
                    "HistorialVacunacion.ProximaDosis, " +
                    "Insumos.Nombre AS NombreVacuna, " +
                    "Insumos.Fabricante, " +
                    "Insumos.UnidadMedida, " +
                    "Insumos.CategoriaID, " +
                    "Insumos.NivelMinimo, " +
                    "Insumos.Stock, " +
                    "Insumos.PrecioUnitario, " +
                    "Insumos.ID_Proveedor " +
                 "FROM HistorialVacunacion " +
                 "INNER JOIN Insumos ON HistorialVacunacion.ID_Vacuna = Insumos.ID " +
                 "INNER JOIN Vacunas ON HistorialVacunacion.ID_Vacuna = Vacunas.ID " +
                 "WHERE HistorialVacunacion.ID_Mascota = ?";
    int params = animal.getId();
    ArrayList<Vacuna> listaVacunas = new ArrayList<>();
    try {
        ResultSet rs = CRUD.consultarDB(sql, params);
        if (rs != null) {
            while (rs.next()) {
                int id = rs.getInt("InsumoID"); // Usar InsumoID, que es el ID de Insumos
                String tipoEspecie = rs.getString("TipoEspecie");
                String lote = rs.getString("Lote");
                LocalDate fechaIngreso = rs.getDate("FechaIngreso").toLocalDate();
                LocalDate fechaVencimiento = rs.getDate("FechaVencimiento").toLocalDate();
                LocalDate fechaAplicacion = rs.getDate("FechaAplicacion").toLocalDate();
                String nombre = rs.getString("NombreVacuna"); // Cambié el alias para el nombre
                String fabricante = rs.getString("Fabricante");
                String unidadMedida = rs.getString("UnidadMedida");
                int categoriaId = rs.getInt("CategoriaID");

                CategoriaInsumos categoria = CategoriaController.obtenerCategoriaPorId(categoriaId);

                int nivelMinimo = rs.getInt("NivelMinimo");
                int cantidadStock = rs.getInt("Stock");
                BigDecimal precioUnitario = rs.getBigDecimal("PrecioUnitario");

                int proveedorId = rs.getInt("ID_Proveedor");

                Proveedor proveedor = ProveedorController.obtenerProveedorPorId(proveedorId);

                Vacuna vacuna = new Vacuna(
                    tipoEspecie,
                    lote,
                    fechaIngreso,
                    fechaVencimiento,
                    fechaAplicacion,
                    id,
                    nombre,
                    fabricante,
                    unidadMedida,
                    categoria,
                    nivelMinimo,
                    cantidadStock,
                    precioUnitario,
                    proveedor
                );
                listaVacunas.add(vacuna);
            }
        }

    } catch (SQLException ex) {
        Logger.getLogger(VacunasController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return listaVacunas;
}
    
    public static ArrayList<Vacuna> mostrarTodasLasVacunasPorEspecie(Animal animal) {
    CRUD.setConexion(ConnectionDB.getConnection());

    String sql = "SELECT v.ID, v.TipoEspecie, v.Lote, v.FechaIngreso, v.FechaVencimiento, i.Nombre, i.Fabricante, i.UnidadMedida, i.CategoriaID, i.NivelMinimo, i.Stock, i.PrecioUnitario, i.ID_Proveedor FROM Vacunas v JOIN Insumos i ON v.ID = i.ID WHERE v.TipoEspecie = ?";

    String params = animal.getSpecie();

    ArrayList<Vacuna> listaVacunas = new ArrayList<>();

    try {
        ResultSet rs = CRUD.consultarDB(sql, params);

        if (rs != null) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String tipoEspecie = rs.getString("TipoEspecie");
                String lote = rs.getString("Lote");
                LocalDate fechaIngreso = rs.getDate("FechaIngreso").toLocalDate();
                LocalDate fechaVencimiento = rs.getDate("FechaVencimiento").toLocalDate();

                String nombre = rs.getString("Nombre");
                String fabricante = rs.getString("Fabricante");
                String unidadMedida = rs.getString("UnidadMedida");
                int categoriaId = rs.getInt("CategoriaID");

                CategoriaInsumos categoria = CategoriaController.obtenerCategoriaPorId(categoriaId);

                int nivelMinimo = rs.getInt("NivelMinimo");
                int cantidadStock = rs.getInt("Stock");
                BigDecimal precioUnitario = rs.getBigDecimal("PrecioUnitario");

                int proveedorId = rs.getInt("ID_Proveedor");

                Proveedor proveedor = ProveedorController.obtenerProveedorPorId(proveedorId);

                Vacuna vacuna = new Vacuna(
                    tipoEspecie,
                    lote,
                    fechaIngreso,
                    fechaVencimiento,
                    id,
                    nombre,
                    fabricante,
                    unidadMedida,
                    categoria,
                    nivelMinimo,
                    cantidadStock,
                    precioUnitario,
                    proveedor
                );
                listaVacunas.add(vacuna);
            }
        }

    } catch (SQLException ex) {
        Logger.getLogger(VacunasController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return listaVacunas;
}
    public static ArrayList<Vacuna> mostrarTodasLasVacunasPorJornadaYEspecie(TipoAnimalInsumo tipoEspecieEnum) {
    CRUD.setConexion(ConnectionDB.getConnection());

    String sql = "SELECT v.ID, v.TipoEspecie, v.Lote, v.FechaIngreso, v.FechaVencimiento, i.Nombre, i.Fabricante, i.UnidadMedida, i.CategoriaID, i.NivelMinimo, i.Stock, i.PrecioUnitario, i.ID_Proveedor FROM Vacunas v JOIN Insumos i ON v.ID = i.ID WHERE v.TipoEspecie = ?";

    String params = tipoEspecieEnum.name();
    

    ArrayList<Vacuna> listaVacunas = new ArrayList<>();

    try {
        ResultSet rs = CRUD.consultarDB(sql, params);

        if (rs != null) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String tipoEspecie = rs.getString("TipoEspecie");
                String lote = rs.getString("Lote");
                LocalDate fechaIngreso = rs.getDate("FechaIngreso").toLocalDate();
                LocalDate fechaVencimiento = rs.getDate("FechaVencimiento").toLocalDate();

                String nombre = rs.getString("Nombre");
                String fabricante = rs.getString("Fabricante");
                String unidadMedida = rs.getString("UnidadMedida");
                int categoriaId = rs.getInt("CategoriaID");

                CategoriaInsumos categoria = CategoriaController.obtenerCategoriaPorId(categoriaId);

                int nivelMinimo = rs.getInt("NivelMinimo");
                int cantidadStock = rs.getInt("Stock");
                BigDecimal precioUnitario = rs.getBigDecimal("PrecioUnitario");

                int proveedorId = rs.getInt("ID_Proveedor");

                Proveedor proveedor = ProveedorController.obtenerProveedorPorId(proveedorId);

                Vacuna vacuna = new Vacuna(
                    tipoEspecie,
                    lote,
                    fechaIngreso,
                    fechaVencimiento,
                    id,
                    nombre,
                    fabricante,
                    unidadMedida,
                    categoria,
                    nivelMinimo,
                    cantidadStock,
                    precioUnitario,
                    proveedor
                );
                listaVacunas.add(vacuna);
            }
        }

    } catch (SQLException ex) {
        Logger.getLogger(VacunasController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return listaVacunas;
}
    
}

