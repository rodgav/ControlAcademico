package com.nationfis.controlacademicononfc.Views.MostrarEstudiantes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by Sam on 20/08/2017.
 */

class EmpaqueEstudiantes {
    private String accion,s1,ep,anioa,sede;
    EmpaqueEstudiantes(String accion, String s1, String ep, String anioa, String sede) {
        this.accion = accion;
        this.s1 = s1;
        this.ep =ep;
        this.anioa = anioa;
        this.sede = sede;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try {
            jo.put("accion",accion);
            jo.put("1",s1);
            jo.put("2",ep);
            jo.put("3",anioa);
            jo.put("4",sede);
            Boolean primer = true;
            Iterator i = jo.keys();
            do {
                String key = i.next().toString();
                String value = jo.get(key).toString();
                if (primer){
                    primer = false;
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
