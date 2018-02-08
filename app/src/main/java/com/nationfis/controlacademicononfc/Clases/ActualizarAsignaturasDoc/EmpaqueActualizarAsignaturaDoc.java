package com.nationfis.controlacademicononfc.Clases.ActualizarAsignaturasDoc;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by GlobalTIC's on 7/02/2018.
 */

class EmpaqueActualizarAsignaturaDoc {
    private String accion,codigo,anioa,sede,codigoa;
    EmpaqueActualizarAsignaturaDoc(String accion, String codigo, String anioa, String sede, String codigoa) {
        this.accion = accion;
        this.codigo = codigo;
        this.anioa = anioa;
        this.sede = sede;
        this.codigoa = codigoa;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try {
            jo.put("accion",accion);
            jo.put("1",codigo);
            jo.put("2",anioa);
            jo.put("3",sede);
            jo.put("4",codigoa);
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
