package edu.pucmm.isc.objetos;

import java.util.List;
import java.util.Date;

public class VentasProducto {
    long id;
    java.util.Date fechaCompra;
    String nombreCliente;
    List<Producto> listaProductos;

    //Constructors
    public VentasProducto() { }

    public VentasProducto(long id, Date fechaCompra, String nombreCliente, List<Producto> listaProductos) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaProductos = listaProductos;
    }

    //Getters and setters
    public void setId(long id) { this.id = id; }

    public long getId() { return id; }

    public void setFechaCompra(Date fechaCompra) { this.fechaCompra = fechaCompra; }

    public Date getFechaCompra() { return fechaCompra; }

    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNombreCliente() { return nombreCliente; }

    public void setListaProductos(List<Producto> listaProductos) { this.listaProductos = listaProductos; }

    public List<Producto> getListaProductos() { return listaProductos; }
}
