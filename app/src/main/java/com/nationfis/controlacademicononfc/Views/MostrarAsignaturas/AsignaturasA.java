package com.nationfis.controlacademicononfc.Views.MostrarAsignaturas;

/*
 * Created by Sam on 18/06/2017.
 */

public class AsignaturasA {
    private String modo,nombrea,nombre,foto;
    private Integer codigo,activo;
    void setModo(String modo) {
        this.modo = modo;
    }

    public void setNombrea(String nombrea) {
        this.nombrea = nombrea;
    }

    public void setCodigo(Integer codigo) {
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

    public Integer getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}
