package com.nationfis.controlacademicononfc.Views.ComprobarNotas;

/*
 * Created by Sam on 06/06/2017.
 */

public class NotasCN {
    private String nombre,foto,nota;
    private Integer codigo;

    public void setNota(String nota) {
        this.nota = nota;
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

    public String getNota() {
        return nota;
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

}
