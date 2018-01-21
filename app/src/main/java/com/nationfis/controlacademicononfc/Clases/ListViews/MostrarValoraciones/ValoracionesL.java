package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarValoraciones;

/*
 * Created by Sam on 18/06/2017.
 */

public class ValoracionesL {
    private String nombre, codigo, nombrea, nombreu,peso;
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombrea(String nombrea) {
        this.nombrea = nombrea;
    }

    void setNombreu(String nombreu) {
        this.nombreu = nombreu;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombrea() {
        return nombrea;
    }

    String getNombreu() {
        return nombreu;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getPeso() {
        return peso;
    }
}
