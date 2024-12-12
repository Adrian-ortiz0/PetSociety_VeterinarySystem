package com.mycompany.petsociety.controllers;

import com.mycompany.petsociety.models.Animal;
import com.mycompany.petsociety.models.HistorialServicios;
import com.mycompany.petsociety.models.HistorialVacunacion;
import com.mycompany.petsociety.models.Servicio;
import com.mycompany.petsociety.models.TipoServicio;
import com.mycompany.petsociety.models.Vacuna;
import com.mycompany.petsociety.models.Veterinarian;
import com.mycompany.petsociety.persistence.CRUD;
import com.mycompany.petsociety.persistence.ConnectionDB;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistorialesController {
    
    public static HashMap<String, Object> calcularTotalesMensuales() {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT " +
                 "YEAR(Fecha) AS Año, " +
                 "MONTH(Fecha) AS Mes, " +
                 "SUM(Total) AS TotalFacturado " +
                 "FROM Facturas " +
                 "GROUP BY YEAR(Fecha), MONTH(Fecha) " +
                 "ORDER BY Año DESC, Mes DESC;";

    HashMap<String, Object> totalesMensuales = new HashMap<>();
    
    try (ResultSet rs = CRUD.consultarDB(sql)) {
        while (rs != null && rs.next()) {
            int año = rs.getInt("Año");
            int mes = rs.getInt("Mes");
            double totalFacturado = rs.getDouble("TotalFacturado");
            
            String claveMes = año + "-" + String.format("%02d", mes);
            
            totalesMensuales.put(claveMes, totalFacturado);
        }
    } catch (SQLException ex) {
        Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        CRUD.closeConexion(CRUD.getConexion());
    }

    return totalesMensuales;
}
    
    public static ArrayList<Veterinarian> veterinariosMasSolicitados(){
        CRUD.setConexion(ConnectionDB.getConnection());
        String sql = "SELECT Veterinarios.ID, Veterinarios.Nombre, Veterinarios.Especialidad, " +
                 "COUNT(HistorialServicios.VeterinarioID) AS CantidadServicios " +
                 "FROM HistorialServicios " +
                 "INNER JOIN Veterinarios ON HistorialServicios.VeterinarioID = Veterinarios.ID " +
                 "GROUP BY Veterinarios.ID, Veterinarios.Nombre, Veterinarios.Especialidad " +
                 "ORDER BY CantidadServicios DESC LIMIT 3";

        ArrayList<Veterinarian> veterinariosTop = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultarDB(sql);

            while (rs != null && rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("Nombre");
                String especialidad = rs.getString("Especialidad");
                int cantidadServicios = rs.getInt("CantidadServicios");

                Veterinarian veterinario = new Veterinarian();
                veterinario.setId(id);
                veterinario.setNombre(nombre);
                veterinario.setEspecialidad(especialidad);


                veterinariosTop.add(veterinario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HistorialesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return veterinariosTop;
    }

  
   
    public static ArrayList<Servicio> serviciosMasSolicitados() {
    CRUD.setConexion(ConnectionDB.getConnection());
    String sql = "SELECT Servicios.ID, Servicios.Nombre, Servicios.TipoServicio, " +
                 "Servicios.CostoBase, COUNT(HistorialServicios.ServicioID) AS CantidadSolicitudes " +
                 "FROM HistorialServicios " +
                 "INNER JOIN Servicios ON HistorialServicios.ServicioID = Servicios.ID " +
                 "GROUP BY Servicios.ID, Servicios.Nombre, Servicios.TipoServicio, Servicios.CostoBase " +
                 "ORDER BY CantidadSolicitudes DESC LIMIT 3";

    ArrayList<Servicio> serviciosTop = new ArrayList<>();

    try {
        ResultSet rs = CRUD.consultarDB(sql);

        while (rs != null && rs.next()) {
            String tipoServicioStr = rs.getString("TipoServicio");
            TipoServicio tipoServicio = TipoServicio.valueOf(tipoServicioStr);

            int id = rs.getInt("ID");
            String nombre = rs.getString("Nombre");
            BigDecimal costoBase = rs.getBigDecimal("CostoBase");
            int cantidadSolicitudes = rs.getInt("CantidadSolicitudes");

            Servicio servicio = new Servicio();
            servicio.setId(id);
            servicio.setNombre(nombre);
            servicio.setTipoServicio(tipoServicio);
            servicio.setCostoBase(costoBase);

            serviciosTop.add(servicio);
        }
    } catch (SQLException ex) {
        Logger.getLogger(HistorialesController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return serviciosTop;
}
    
        public static int numeroDeVisitasPorMascota(Animal pet) {
            int numeroVisitas = 0;
            CRUD.setConexion(ConnectionDB.getConnection());

            String sql = "SELECT COUNT(*) AS NumeroServicios FROM HistorialServicios WHERE MascotaID = ?";
            ResultSet rs = null;

            try {
                rs = CRUD.consultarDB(sql, pet.getId());
                if (rs != null && rs.next()) {
                    numeroVisitas = rs.getInt("NumeroServicios");
                }
            } catch (SQLException ex) {
                Logger.getLogger(PetController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(PetController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                CRUD.closeConexion(CRUD.getConexion());
            }

            return numeroVisitas;
        }
    
        public static ArrayList<HistorialServicios> mostrarServicios(Animal pet) {
        ArrayList<HistorialServicios> listaServicios = new ArrayList<>();
        CRUD.setConexion(ConnectionDB.getConnection());

        String sql = "SELECT hs.ID AS HistorialID, " +
                     "hs.FechaAplicacion, " +
                     "hs.ProximaDosis, " +
                     "s.Nombre AS NombreServicio, " +
                     "s.TipoServicio, " +
                     "s.CostoBase, " +
                     "vet.Nombre AS VeterinarioNombre " +
                     "FROM HistorialServicios hs " +
                     "JOIN Servicios s ON hs.ServicioID = s.ID " +
                     "JOIN Veterinarios vet ON hs.VeterinarioID = vet.ID " +
                     "WHERE hs.MascotaID = ?;";

        try {
            ResultSet rs = CRUD.consultarDB(sql, pet.getId());
            while (rs != null && rs.next()) {
                int historialID = rs.getInt("HistorialID");
                LocalDate fechaAplicacion = rs.getDate("FechaAplicacion").toLocalDate();
                LocalDate proximaDosis = rs.getDate("ProximaDosis") != null ? rs.getDate("ProximaDosis").toLocalDate() : null;

                String nombreServicio = rs.getString("NombreServicio");
                String tipoServicioStr = rs.getString("TipoServicio");

                TipoServicio tipoServicio = null;
                if (tipoServicioStr != null) {
                    tipoServicio = TipoServicio.valueOf(tipoServicioStr);
                }

                BigDecimal costoBase = rs.getBigDecimal("CostoBase");
                String veterinarioNombre = rs.getString("VeterinarioNombre");

                Veterinarian veterinarian = new Veterinarian();
                veterinarian.setNombre(veterinarioNombre);

                Servicio servicio = new Servicio();
                servicio.setNombre(nombreServicio);
                servicio.setTipoServicio(tipoServicio);
                servicio.setCostoBase(costoBase);

                HistorialServicios historialServicio = new HistorialServicios(historialID, pet, servicio, veterinarian, fechaAplicacion, proximaDosis);
                listaServicios.add(historialServicio);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HistorialesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaServicios;
    }
    
    
    public static ArrayList<HistorialVacunacion> mostrarServiciosPorTipo(Animal pet) {
    ArrayList<HistorialVacunacion> listaHistorial = new ArrayList<>();
    CRUD.setConexion(ConnectionDB.getConnection());

     String sql = "SELECT hv.ID AS ID, hv.ID_Mascota, hv.FechaAplicacion, hv.ProximaDosis, " +
             "v.ID AS ID_Vacuna, v.Lote AS LoteVacuna, v.FechaIngreso, v.FechaVencimiento, " +
             "i.Nombre AS NombreVacuna, " +  
             "vet.ID AS ID_Veterinario, vet.Nombre AS VeterinarioNombre " +
             "FROM HistorialVacunacion hv " +
             "JOIN Vacunas v ON hv.ID_Vacuna = v.ID " +  
             "JOIN Veterinarios vet ON hv.ID_Veterinario = vet.ID " +
             "JOIN Insumos i ON v.ID = i.ID " +  
             "WHERE hv.ID_Mascota = ?;";

    try {
    ResultSet rs = CRUD.consultarDB(sql, pet.getId());
    while (rs != null && rs.next()) {
        int id = rs.getInt("ID");
        LocalDateTime fechaAplicacion = rs.getDate("FechaAplicacion").toLocalDate().atStartOfDay();
        LocalDateTime proximaDosis = rs.getDate("ProximaDosis").toLocalDate().atStartOfDay();
        Animal anima = new Animal();
        anima.setId(rs.getInt("ID_Mascota"));
        Vacuna vacuna = new Vacuna();
        vacuna.setId(rs.getInt("ID_Vacuna"));
        String nombreVacuna = rs.getString("NombreVacuna");
        vacuna.setNombre(nombreVacuna);  
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setId(rs.getInt("ID_Veterinario"));
        HistorialVacunacion historial = new HistorialVacunacion(id, pet, vacuna, veterinarian, fechaAplicacion, proximaDosis);
        listaHistorial.add(historial);
        }
    } catch (SQLException ex) {
        Logger.getLogger(HistorialesController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return listaHistorial;
}
}
