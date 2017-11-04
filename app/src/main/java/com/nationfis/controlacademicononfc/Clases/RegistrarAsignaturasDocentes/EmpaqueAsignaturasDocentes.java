package com.nationfis.controlacademicononfc.Clases.RegistrarAsignaturasDocentes;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Sam on 02/05/2017.
 */

public class EmpaqueAsignaturasDocentes {
    private String codio,codigo1;
    public EmpaqueAsignaturasDocentes(String codio, String codigo1) {
        this.codigo1 = codigo1;
        this.codio = codio;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        String accion= MD5.encrypt("registrarasigdoc");
        try {
            jo.put("accion",accion);
            jo.put("1",codigo1);
            jo.put("2",codio);
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
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
