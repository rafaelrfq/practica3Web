package edu.pucmm.isc.objetos;

import java.util.List;

public class CarroCompra {
    long id;
    List<Producto> listaProductos;

    //Constructors
    public CarroCompra() { }

    public CarroCompra(long id, List<Producto> listaProductos) {
        this.id = id;
        this.listaProductos = listaProductos;
    }

    //Getters and setters
    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public List<Producto> getListaProductos() { return listaProductos; }

    public void setListaProductos(List<Producto> listaProductos) { this.listaProductos = listaProductos; }

    public boolean borrarProducto(Producto producto){ return listaProductos.remove(producto); }
}
