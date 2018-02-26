package com.nationfis.controlacademicononfc.Views.MostrarAllNotas;

/*
 * Created by GlobalTIC's on 25/02/2018.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

class EmpaqueNotas {
    private String accion,anioa,codigo;

    EmpaqueNotas(String accion, String anioa, String codigo) {
        this.accion = accion;
        this.anioa = anioa;
        this.codigo = codigo;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try {
            jo.put("accion",accion);
            jo.put("1",anioa);
            jo.put("2",codigo);
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
