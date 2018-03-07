package com.nationfis.controlacademicononfc.Clases.ActualizarHorario;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by GlobalTIC's on 7/02/2018.
 */

class EmpaqueActualizarHorario {
    private String accion,anioa,inicio1,fin1;
    private Integer codigoa,sede,dia1;
    EmpaqueActualizarHorario(String accion, Integer codigoa, String anioa, Integer sede, Integer dia1, String inicio1, String fin1) {
        this.accion = accion;
        this.codigoa = codigoa;
        this.anioa = anioa;
        this.sede = sede;
        this.dia1 = dia1;
        this.inicio1 = inicio1;
        this.fin1 = fin1;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try {
            jo.put("accion",accion);
            jo.put("1",codigoa);
            jo.put("2",anioa);
            jo.put("3",sede);
            jo.put("4",dia1);
            jo.put("5",inicio1);
            jo.put("6",fin1);
            Boolean primero = true;
            Iterator i = jo.keys();
            do {
                String key = i.next().toString();
                String value = jo.get(key).toString();
                if (primero){
                    primero = false;
                }else {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key,"UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value,"UTF-8"));
            }while (i.hasNext());
            return sb.toString();
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
