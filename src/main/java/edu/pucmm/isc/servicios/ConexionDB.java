package edu.pucmm.isc.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionDB {

    private static ConexionDB conn;
    private String URL = "jdbc:h2:tcp://localhost/~/practica3";

    private ConexionDB() { registerDriver(); }

    public static ConexionDB getInstance() {
        if(conn == null){
            conn = new ConexionDB();
        }
        return conn;
    }

    private void registerDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public void testConn() {
        try {
            getConn().close();
            System.out.println("Conexión exitosa!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}