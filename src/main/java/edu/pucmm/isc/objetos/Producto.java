package edu.pucmm.isc.objetos;

import java.math.BigDecimal;

public class Producto {
    int id;
    String nombre;
    BigDecimal precio;
    int cantidad;

    //Constructors
    public Producto() { }

    //Constructor sin cantidad (para agregar al listado)
    public Producto(int id, String nombre, BigDecimal precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    //Constructor con cantidad (para agregar a carrito y posteriormente a ventas)
    public Producto(int id, String nombre, BigDecimal precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    //Getters and setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }

    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getCantidad() { return cantidad; }

    //Actualizador
    public void actualizar(Producto prod){
        id = prod.getId();
        nombre = prod.getNombre();
        precio = prod.getPrecio();
    }
}
