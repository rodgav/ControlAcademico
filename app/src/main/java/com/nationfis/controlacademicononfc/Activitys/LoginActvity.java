package com.nationfis.controlacademicononfc.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nationfis.controlacademicononfc.Clases.Datos.UsuariosSqlite;
import com.nationfis.controlacademicononfc.Fragments.LoginForOneTouch;
import com.nationfis.controlacademicononfc.Fragments.LoginFragment;
import com.nationfis.controlacademicononfc.R;

public class LoginActvity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String tipo = preferences.getString("a", "");
        String nombre = preferences.getString("nombre", "");
        String codigo = preferences.getString("codigo", "");
        String image = preferences.getString("image", "");
        String tipos = preferences.getString("tipos", "");

        UsuariosSqlite usuariosSqlite = new UsuariosSqlite(LoginActvity.this,"UsersDB.sqlite",null,1);
        usuariosSqlite.queryData("CREATE TABLE IF NOT EXISTS USERS(Id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR , codigo VARCHAR unique, image VARCHAR, tipo VARCHAR)");

        Cursor cursor = usuariosSqlite.getData("SELECT * FROM USERS");

        if(savedInstanceState == null){
            if(tipo.length()>0 || nombre.length()>0 || codigo.length()>0 || image.length()>0 || tipos.length()>0){
                Intent intent = new Intent(this,NavigationActivity.class);
                startActivity(intent);
                finish();
            }else if(cursor.moveToFirst()){
                Fragment fragment = new LoginForOneTouch();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.contenedor,fragment);
                fragmentTransaction.commit();
            }else{
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new LoginFragment();
                fragmentTransaction.add(R.id.contenedor,fragment);
                fragmentTransaction.commit();
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().commit();
        }else {
            super.onBackPressed();
        }
    }
}
