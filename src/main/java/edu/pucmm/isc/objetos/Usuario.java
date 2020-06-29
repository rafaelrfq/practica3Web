package edu.pucmm.isc.objetos;

public class Usuario {
    String usuario;
    String nombre;
    String password;

    //Constructors
    public Usuario() { }

    public Usuario(String usuario, String nombre, String password) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.password = password;
    }
    //Getters and setters
    public String getUsuario() { return usuario; }

    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
