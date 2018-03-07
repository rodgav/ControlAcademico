package com.nationfis.controlacademicononfc.Clases.EliminarHorario;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by GlobalTIC's on 7/02/2018.
 */

class EmpaqueEliminarHorario {
    private String accion,inicio,fin,anioa;
    private int codigoa,idd,sede;
    EmpaqueEliminarHorario(String accion, Integer codigoa, Integer idd, Integer sede, String inicio, String fin, String anioa) {
        this.accion = accion;
        this.codigoa = codigoa;
        this.idd = idd;
        this.sede = sede;
        this.inicio = inicio;
        this.fin = fin;
        this.anioa = anioa;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try{
            jo.put("accion",accion);
            jo.put("1",codigoa);
            jo.put("2",idd);
            jo.put("3",sede);
            jo.put("4",inicio);
            jo.put("5",fin);
            jo.put("6",anioa);

            Boolean primervalor = true;
            Iterator it = jo.keys();
            do {
                String key=it.next().toString();
                String value=jo.get(key).toString();
                if(primervalor){
                    primervalor = false;
                }else{
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key,"UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value,"UTF-8"));
            }while (it.hasNext());
            return sb.toString();
        }catch (JSONException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }
}
