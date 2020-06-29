package edu.pucmm.isc.servicios;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManagement {
    private static DataBaseManagement db;
    private Server tcp;

    private DataBaseManagement(){ }

    public static DataBaseManagement getInstance() {
        if(db == null){
            db = new DataBaseManagement();
        }
        return db;
    }

    public void startDB() throws SQLException {
        // Se crea el servidor
        tcp = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-tcpDaemon", "-ifNotExists").start();
    }

    public void stopDB() throws SQLException {
        // Se detiene el servidor
        //Server.shutdownTcpServer("tcp://localhost:9092", "", false, false);
        tcp.stop();
    }

    // Creacion de tablas iniciales
    public static void crearTablas() throws  SQLException{
        String producto = "CREATE TABLE IF NOT EXISTS PRODUCTO (ID INT IDENTITY PRIMARY KEY NOT NULL, " +
                "NOMBRE VARCHAR(100) NOT NULL, PRECIO DECIMAL NOT NULL);";
        String ventaProducto = "CREATE TABLE IF NOT EXISTS VENTAPRODUCTO (ID INT IDENTITY PRIMARY KEY NOT NULL, " +
                "FECHACOMPRA DATE NOT NULL, NOMBRECLIENTE VARCHAR(100) NOT NULL);";
        String cantidadVtaProd = "CREATE TABLE IF NOT EXISTS CANTVENTAPROD (VENTA INT NOT NULL, " +
                "PRODUCTO INT NOT NULL, CANTIDAD INT NOT NULL, PRIMARY KEY (VENTA, PRODUCTO), " +
                "CONSTRAINT FK_VENTAPROD_VTA FOREIGN KEY (VENTA) REFERENCES VENTAPRODUCTO(ID), " +
                "CONSTRAINT FK_VENTAPROD_PROD FOREIGN KEY (PRODUCTO) REFERENCES PRODUCTO(ID));";
        String usuarios = "CREATE TABLE IF NOT EXISTS USUARIO (USUARIO VARCHAR(100) PRIMARY KEY NOT NULL, " +
                "NOMBRE VARCHAR(100) NOT NULL, PASSWORD VARCHAR(100) NOT NULL);";

        Connection conn = ConexionDB.getInstance().getConn();
        Statement statement = conn.createStatement();
        statement.execute(producto);
        statement.execute(ventaProducto);
        statement.execute(cantidadVtaProd);
        statement.execute(usuarios);
        statement.close();
        conn.close();
    }

    // Creacion de los usuarios por defecto
    public static void crearUsuarios(){
        try {
            Connection conn = ConexionDB.getInstance().getConn();
            String query = "MERGE INTO USUARIO VALUES(?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(query);
            PreparedStatement ps1 = conn.prepareStatement(query);

            ps.setString(1, "admin");
            ps.setString(2, "Administrador");
            ps.setString(3, "admin");
            ps.executeUpdate();

            ps1.setString(1, "rafael");
            ps1.setString(2, "Rafael Felipe");
            ps1.setString(3, "0712");
            ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}