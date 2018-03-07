package com.nationfis.controlacademicononfc.Clases.RegistrarMatricula;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by SamGM on 22/04/2017.
 */

class EmpaqueMatricula {
    private String baucher1,anio,matricula1;
    private Integer codigo1,escuela,an,tipom,activo,sede;
    EmpaqueMatricula(String baucher1, Integer codigo1, String matricula1, String anio, Integer escuela, Integer an, Integer tipom, Integer activo, Integer sede) {
        this.baucher1 = baucher1;
        this.codigo1 = codigo1;
        this.matricula1 = matricula1;
        this.anio = anio;
        this.escuela = escuela;
        this.an = an;
        this.tipom = tipom;
        this.activo = activo;
        this.sede = sede;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        String accion= MD5.encrypt("registrarmatri");
        try {
            jo.put("accion",accion);
            jo.put("baucher1",baucher1);
            jo.put("codigo1",codigo1);
            jo.put("matricula1",matricula1);
            jo.put("anio",anio);
            jo.put("escuela",escuela);
            jo.put("an",an);
            jo.put("tipom",tipom);
            jo.put("1",activo);
            jo.put("2",sede);
            Boolean primer = true;
            Iterator it = jo.keys();
            do {
                String key = it.next().toString();
                String value = jo.get(key).toString();
                if (primer){
                    primer = false;
                }else {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key,"UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value,"UTF-8"));
            }while (it.hasNext());
            return sb.toString();
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
