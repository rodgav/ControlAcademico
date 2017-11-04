package com.nationfis.controlacademicononfc.Clases.Login;

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

/**
 * Created by SamGM on 13/04/2017.
 */

class AnalizadorLogin extends AsyncTask<Void,Void,Integer> {
    private Context c;
    private String nombres,apellidop,apellidm,image,correo,tdoc,ndoc,codigo,tipoid,
            genero,ntelefono,fnacimiento,lnacimiento,activo,ep,sede,nombre,nombredocumento,
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

            if (Objects.equals(activo,"1")){
                Intent intent = new Intent(c,NavigationActivity.class);
                SharedPreferences preferences = c.getSharedPreferences("datos",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("a", MD5.encrypt(tipo));
                editor.putString("codigo", codigo);
                editor.putString("nombre", nombre);
                editor.putString("image", image);
                editor.putString("tipos",tipo);
                editor.putString("ep",ep);
                editor.putString("sede",sede);
                editor.putString("seden",nombresede);
                editor.putString("epn",nombreescuela);
                editor.apply();

                Toast.makeText(c, "Inicio como " + tipo, Toast.LENGTH_SHORT).show();
                c.startActivity(intent);
            }else if (Objects.equals(activo,"0")){
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("NECESITA ACTIVAR")
                        .setMessage("Complete su registro activando su cuenta," +
                                " acerquese a su secretaria")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(c,LoginActvity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        c.startActivity(intent);
                                    }
                                })
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(c,LoginActvity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                c.startActivity(intent);
                            }
                        });
                builder.show();
            }
            else{
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
                tdoc = jo.getString("tdoc");
                ndoc = jo.getString("ndoc");
                codigo = jo.getString("codigo");
                tipoid = jo.getString("tipoid");
                genero  = jo.getString("genero");
                ntelefono  = jo.getString("ntelefono");
                fnacimiento  = jo.getString("fnacimiento");
                lnacimiento  = jo.getString("lnacimiento");
                activo  = jo.getString("activo");
                ep = jo.getString("ep");
                sede = jo.getString("sede");
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
