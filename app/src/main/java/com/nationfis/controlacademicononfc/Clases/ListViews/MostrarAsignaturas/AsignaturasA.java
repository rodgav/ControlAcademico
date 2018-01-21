package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarAsignaturas;

/**
 * Created by Sam on 18/06/2017.
 */

public class AsignaturasA {
    private String modo,nombrea,codigo,nombre,foto,activo;
    void setModo(String modo) {
        this.modo = modo;
    }

    public void setNombrea(String nombrea) {
        this.nombrea = nombrea;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    String getModo() {
        return modo;
    }

    public String getNombrea() {
        return nombrea;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
