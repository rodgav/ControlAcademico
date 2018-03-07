package com.nationfis.controlacademicononfc.Clases.Datos;


/*
 * Created by Sam on 19/04/2017.
 */

public class UsuariosUsuarios {
    private int id,codigo;
    private String nombre,imagen,tipo;
    public UsuariosUsuarios(int id, String nombre, Integer codigo, String imagen,String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.imagen = imagen;
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }
    public String getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}
