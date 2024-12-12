    package com.mycompany.petsociety.persistence;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.logging.Level;
    import java.util.logging.Logger;

    public class CRUD {
        private static Connection conexion;

        public static Connection getConexion() {
            return conexion;
        }

        public static Connection setConexion(Connection nuevaConexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        conexion = nuevaConexion;
        return conexion;
    }

        public static void closeConexion(Connection conexion){
            if(conexion != null){
                try {
                    conexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public static boolean insertarDB(String sql, Object... params) {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean eliminarDB(String sql, Object... params) {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean updateDB(String sql, Object... params) {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static ResultSet consultarDB(String sql, Object... params) {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException closeEx) {
                    Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, closeEx);
                }
            }
            return null;
        }
    }
    }
