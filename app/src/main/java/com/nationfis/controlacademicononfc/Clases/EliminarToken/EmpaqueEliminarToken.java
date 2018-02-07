package com.nationfis.controlacademicononfc.Clases.EliminarToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/*
 * Created by GlobalTIC's on 31/01/2018.
 */

class EmpaqueEliminarToken {
    private String accion,codigo,token;
    EmpaqueEliminarToken(String accion, String codigo, String token) {
        this.accion = accion;
        this.codigo = codigo;
        this.token = token;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuilder sb = new StringBuilder();
        try{
            jo.put("accion",accion);
            jo.put("1",codigo);
            jo.put("2",token);
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
