package com.nationfis.controlacademicononfc.Views.ComprobarAsistencia;

/*
 * Created by Sam on 28/04/2017.
 */

public class AsistenciaCA {
    private String nombre,foto,asignatura;
    private Integer codigo,asistio;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getFoto() {
        return foto;
    }

    void setAsistio(Integer asistio) {
        this.asistio = asistio;
    }

    Integer getAsistio() {
        return asistio;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getAsignatura() {
        return asignatura;
    }
}
