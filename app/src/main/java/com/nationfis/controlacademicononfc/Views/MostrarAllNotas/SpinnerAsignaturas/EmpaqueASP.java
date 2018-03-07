package com.nationfis.controlacademicononfc.Views.MostrarAllNotas.SpinnerAsignaturas;

/*
 * Created by GlobalTIC's on 25/02/2018.
 */

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

class EmpaqueASP {
    private Integer codigo;
    EmpaqueASP(Integer codigo) {
        this.codigo = codigo;
    }

    String packageData() {
        String accion = MD5.encrypt("asignaturasdoc");
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try {
            jo.put("accion",accion);
            jo.put("codigo",codigo);
            Iterator i = jo.keys();
            Boolean primer = true;
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
