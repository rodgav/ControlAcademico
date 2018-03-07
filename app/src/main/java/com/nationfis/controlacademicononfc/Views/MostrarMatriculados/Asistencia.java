package com.nationfis.controlacademicononfc.Views.MostrarMatriculados;

/*
 * Created by SamGM on 23/04/2017.
 */

public class Asistencia {
    private String nombre,foto;
    private Integer codigo,activo;
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}
