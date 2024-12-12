package com.mycompany.petsociety.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    
    private static final String URL = "jdbc:mysql://localhost:3306/pet_society"; 
    private static final String USER = "root";
    private static final String PASSWORD = "7328";
    
    
    
    public static Connection getConnection() {  
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            
            return conexion;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error trying to connect to database: " + e.getMessage());
            
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException closeEx) {
                    System.out.println("Error closing connection: " + closeEx.getMessage());
                }
            }
        }
        return null;
    }
    
    public static void closeConnection(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

}
