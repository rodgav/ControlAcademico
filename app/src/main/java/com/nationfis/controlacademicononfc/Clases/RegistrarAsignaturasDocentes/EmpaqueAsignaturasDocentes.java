package com.nationfis.controlacademicononfc.Clases.RegistrarAsignaturasDocentes;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by Sam on 02/05/2017.
 */

class EmpaqueAsignaturasDocentes {
    private String anioa;
    private Integer codio,codigo1,sede;
    EmpaqueAsignaturasDocentes(Integer codio, Integer codigo1, Integer sede, String anioa) {
        this.codigo1 = codigo1;
        this.codio = codio;
        this.sede = sede;
        this.anioa = anioa;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        String accion= MD5.encrypt("registrarasigdoc");
        try {
            jo.put("accion",accion);
            jo.put("1",codigo1);
            jo.put("2",codio);
            jo.put("3",sede);
            jo.put("4",anioa);
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
