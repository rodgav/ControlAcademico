package com.nationfis.controlacademicononfc.Clases.Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Activitys.LoginActvity;
import com.nationfis.controlacademicononfc.Activitys.NavigationActivity;
import com.nationfis.controlacademicononfc.Clases.Datos.UsuariosSqlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/*
 * Created by SamGM on 13/04/2017.
 */

class AnalizadorLogin extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private Integer tdoc,ndoc,codigo,tipoid,genero,ntelefono,activo,ep,sede;
    private String nombres,apellidop,apellidm,image,correo,
            fnacimiento,lnacimiento,nombre,nombredocumento,
            tipo,nombregenero,nombredistrito,nombreescuela,nombresede,s;
    AnalizadorLogin(Context c, String s) {
        this.c = c;
        this.s = s;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1){

            if (Objects.equals(activo,1)){
                Intent intent = new Intent(c,NavigationActivity.class);
                SharedPreferences preferences = c.getSharedPreferences("datos",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("a", MD5.encrypt(tipo));
                editor.putInt("codigo", codigo);
                editor.putString("nombre", nombre);
                editor.putString("nombres",nombres);
                editor.putString("apellidop",apellidop);
                editor.putString("apellidom",apellidm);
                editor.putString("image", image);
                editor.putString("tipos",tipo);
                editor.putInt("ep",ep);
                editor.putInt("sede",sede);
                editor.putString("seden",nombresede);
                editor.putString("epn",nombreescuela);
                editor.putString("tdoc",nombredocumento);
                editor.putInt("ndoc",ndoc);
                editor.putString("genero",nombregenero);
                editor.putString("lnacimiento",nombredistrito);
                editor.putString("fnacimiento",fnacimiento);
                editor.putString("correo",correo);
                editor.putInt("telefono",ntelefono);
                editor.putInt("activo",activo);

                editor.apply();

                Toast.makeText(c, "Inicio como " + tipo, Toast.LENGTH_SHORT).show();
                c.startActivity(intent);
                ((Activity)(c)).finish();
            }else if (Objects.equals(activo,0)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("NECESITA ACTIVAR")
                        .setMessage("Complete su registro activando su cuenta," +
                                " acerquese a su secretaria")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(c, LoginActvity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        c.startActivity(intent);
                                    }
                                })
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(c, LoginActvity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                c.startActivity(intent);
                            }
                        });
                builder.show();
            }else{
                Toast.makeText(c,"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = c.getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                /*Intent intent = new Intent(c,LoginActvity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                c.startActivity(intent);*/
            }

        }else {
            Toast.makeText(c,"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = c.getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            /*Intent intent = new Intent(c,LoginActvity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            c.startActivity(intent);*/
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.parse();
    }

    private int parse() {
        try{
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            for(int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                nombres = jo.getString("nombres");
                apellidop = jo.getString("apellidop");
                apellidm = jo.getString("apellidom");
                image = jo.getString("imagen");
                correo = jo.getString("correo");
                tdoc = jo.getInt("tdoc");
                ndoc = jo.getInt("ndoc");
                codigo = jo.getInt("codigo");
                tipoid = jo.getInt("tipoid");
                genero  = jo.getInt("genero");
                ntelefono  = jo.getInt("ntelefono");
                fnacimiento  = jo.getString("fnacimiento");
                lnacimiento  = jo.getString("lnacimiento");
                activo  = jo.getInt("activo");
                ep = jo.getInt("ep");
                sede = jo.getInt("sede");
                nombre = jo.getString("nombre");
                nombredocumento = jo.getString("nombredocumento");
                tipo = jo.getString("tipo");
                nombregenero = jo.getString("nombregenero");
                nombredistrito = jo.getString("nombredistrito");
                nombreescuela = jo.getString("nombreescuela");
                nombresede = jo.getString("nombresede");

                try {
                    UsuariosSqlite usuariosSqlite = new UsuariosSqlite(c,"UsersDB.sqlite",null,1);
                    usuariosSqlite.insertData(nombre,codigo,image,tipo);
                    usuariosSqlite.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return 1;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return 0;
    }
}
