package com.nationfis.controlacademicononfc.Views.ComprobarAsistencia;

/*
 * Created by Sam on 28/04/2017.
 */

public class AsistenciaCA {
    private String nombre,codigo,foto,asistio;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getFoto() {
        return foto;
    }

    void setAsistio(String asistio) {
        this.asistio = asistio;
    }

    String getAsistio() {
        return asistio;
    }
}
