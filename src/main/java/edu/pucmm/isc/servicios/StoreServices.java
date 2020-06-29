package edu.pucmm.isc.servicios;

import edu.pucmm.isc.objetos.CarroCompra;
import edu.pucmm.isc.objetos.Producto;
import edu.pucmm.isc.objetos.Usuario;
import edu.pucmm.isc.objetos.VentasProducto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StoreServices {

    private static StoreServices tienda;
    private CarroCompra carrito;
    private boolean usr = false;
    private boolean adm = false;

    private StoreServices(){

    }

    //Instancia singleton
    public static StoreServices getInstance(){
        if(tienda == null){
            tienda = new StoreServices();
        }
        return tienda;
    }

    public List<Producto> getListaProductos(){
        List<Producto> listaprod = new ArrayList<Producto>();
        Connection con = null;
        try {
            String query = "SELECT * FROM PRODUCTO;";
            con = ConexionDB.getInstance().getConn();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Producto producto = new Producto();
                producto.setId(rs.getInt("ID"));
                producto.setNombre(rs.getString("NOMBRE"));
                producto.setPrecio(rs.getBigDecimal("PRECIO"));

                listaprod.add(producto);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaprod;
    }

    public List<VentasProducto> getListaVentas(){
        List<VentasProducto> ventas = new ArrayList<VentasProducto>();
        Connection con = null;
        try {
            String queryVta = "SELECT * FROM VENTAPRODUCTO;";
            con = ConexionDB.getInstance().getConn();
            PreparedStatement ps = con.prepareStatement(queryVta);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                VentasProducto venta = new VentasProducto();
                venta.setId(rs.getInt("ID"));
                venta.setFechaCompra(rs.getDate("FECHACOMPRA"));
                venta.setNombreCliente(rs.getString("NOMBRECLIENTE"));
                List<Producto> listaProd = new ArrayList<Producto>();
                venta.setListaProductos(listaProd);
                ventas.add(venta);
            }

            String queryProd = "SELECT * FROM CANTVENTAPROD WHERE VENTA = ?";
            for(int i = 0; i < ventas.size(); i++) {
                PreparedStatement ps1 = con.prepareStatement(queryProd);
                ps1.setInt(1, (int)ventas.get(i).getId());
                ResultSet rs1 = ps1.executeQuery();
                while(rs1.next()){
                    int idProd = rs1.getInt("PRODUCTO");
                    int cant = rs1.getInt("CANTIDAD");
                    Producto tmp = getProductoPorID(idProd);
                    tmp.setCantidad(cant);
                    ventas.get(i).getListaProductos().add(tmp);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return ventas;
    }

    public CarroCompra getCarrito() { return carrito; }

    public void setCarrito(CarroCompra cart) { this.carrito = cart; }

    public boolean getUsr() { return usr; }

    public void setUsr(boolean loggeado) { usr = loggeado; }

    public boolean getAdm() { return adm; }

    public void setAdm(boolean admin) { adm = admin; }

    // Productos y lista de productos

    public Producto getProductoPorID(int id){
        Connection con = null;
        Producto producto = null;
        try {
            String query = "SELECT * FROM PRODUCTO WHERE ID = ?";
            con = ConexionDB.getInstance().getConn();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                producto = new Producto();
                producto.setId(rs.getInt("ID"));
                producto.setNombre(rs.getString("NOMBRE"));
                producto.setPrecio(rs.getBigDecimal("PRECIO"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return producto;
    }

    public boolean insertarProductoDB(Producto producto){
        boolean ok =false;

        Connection con = null;
        try {
            String query = "INSERT INTO PRODUCTO(NOMBRE, PRECIO) VALUES(?, ?)";
            con = ConexionDB.getInstance().getConn();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, producto.getNombre());
            ps.setBigDecimal(2, producto.getPrecio());
            int check = ps.executeUpdate();
            ok = check > 0 ;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return ok;
    }

    public boolean actualizarProducto(Producto producto) {
        boolean ok = false;
        Connection con = null;
        try {
            String query = "UPDATE PRODUCTO SET NOMBRE = ?, PRECIO = ? WHERE ID = ?";
            con = ConexionDB.getInstance().getConn();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, producto.getNombre());
            ps.setBigDecimal(2, producto.getPrecio());
            ps.setInt(3, producto.getId());

            int check = ps.executeUpdate();
            ok = check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public boolean eliminarProducto(Producto producto){
        boolean ok = false;
        Connection con = null;
        try {
            String query = "DELETE FROM PRODUCTO WHERE ID = ?";
            con = ConexionDB.getInstance().getConn();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, producto.getId());

            int check = ps.executeUpdate();
            ok = check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    // Carrito

    public Producto getProductoEnCarrito(int id){
        return carrito.getListaProductos().stream().filter(producto -> producto.getId() == id).findFirst().orElse(null);
    }

    public void limpiarCarrito(){
        List<Producto> tmp = new ArrayList<Producto>();
        carrito.setListaProductos(tmp);
    }

    public boolean procesarVenta(VentasProducto venta){
        boolean ok = false;
        Connection con = null;
        try {
            String query = "INSERT INTO VENTAPRODUCTO(FECHACOMPRA, NOMBRECLIENTE) VALUES(?, ?)";
            con = ConexionDB.getInstance().getConn();
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new java.sql.Date(venta.getFechaCompra().getTime()));
            ps.setString(2, venta.getNombreCliente());
            int check = ps.executeUpdate();
            ok = check > 0;

            int idVenta = 0;
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                idVenta = generatedKeys.getInt(1);
            }

            String query2 = "INSERT INTO CANTVENTAPROD(VENTA, PRODUCTO, CANTIDAD) VALUES(?, ?, ?)";
            PreparedStatement ps1 = con.prepareStatement(query2);
            for(int i=0; i<venta.getListaProductos().size(); i++) {
                ps1.setInt(1, idVenta);
                ps1.setInt(2, venta.getListaProductos().get(i).getId());
                ps1.setInt(3, venta.getListaProductos().get(i).getCantidad());
                ps1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public Usuario getUsuarioPorNombreUsuario(String usr){
        Usuario usuario = null;
        Connection con = null;
        try {
            String query = "SELECT * FROM USUARIO WHERE USUARIO = ?";
            con = ConexionDB.getInstance().getConn();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, usr);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                usuario = new Usuario();
                usuario.setUsuario(rs.getString("USUARIO"));
                usuario.setNombre(rs.getString("NOMBRE"));
                usuario.setPassword(rs.getString("PASSWORD"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return usuario;
    }

    public List<Usuario> getListaUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        Connection con = null;
        try {
            String query = "SELECT * FROM USUARIO;";
            con = ConexionDB.getInstance().getConn();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setUsuario(rs.getString("USUARIO"));
                usuario.setNombre(rs.getString("NOMBRE"));
                usuario.setPassword(rs.getString("PASSWORD"));
                listaUsuarios.add(usuario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaUsuarios;
    }

    public Usuario loginUsuario(String usuario, String passw){
        Usuario tmp = getUsuarioPorNombreUsuario(usuario);
        if(tmp == null) {
            throw new RuntimeException("Usuario no existente!");
        } else if(tmp.getUsuario().equals("admin") && tmp.getPassword().equals("admin")) {
            adm = true;
            usr = false;
            return tmp;
        } else if(tmp.getUsuario().equals(usuario) && tmp.getPassword().equals(passw)) {
            usr = true;
            adm = false;
            return tmp;
        } else throw new RuntimeException("Password incorrecto!");
    }

    public void logoutUsuario() {
        usr = false;
        adm = false;
    }
}
