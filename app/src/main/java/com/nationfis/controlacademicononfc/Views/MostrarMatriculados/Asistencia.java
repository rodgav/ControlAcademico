package com.nationfis.controlacademicononfc.Views.MostrarMatriculados;

/*
 * Created by SamGM on 23/04/2017.
 */

public class Asistencia {
    private String nombre,foto,codigo,activo;
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
