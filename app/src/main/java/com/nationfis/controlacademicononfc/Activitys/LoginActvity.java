package com.nationfis.controlacademicononfc.Activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.nationfis.controlacademicononfc.Clases.Datos.UsuariosSqlite;
import com.nationfis.controlacademicononfc.Fragments.LoginForOneTouch;
import com.nationfis.controlacademicononfc.Fragments.LoginFragment;
import com.nationfis.controlacademicononfc.R;


public class LoginActvity extends AppCompatActivity {
    final static int permsRequestCode = 0;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);

        MobileAds.initialize(this, getResources().getString(R.string.interstitial_app_id));
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        /*MobileAds.initialize(this, "ca-app-pub-4761500786576152~8215465788");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");*/
        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        //preferences.edit().remove("datos").apply();
        String tipo = preferences.getString("a", "");
        String nombre = preferences.getString("nombre", "");
        Integer codigo = preferences.getInt("codigo", 0);
        String image = preferences.getString("image", "");
        String tipos = preferences.getString("tipos", "");

        UsuariosSqlite usuariosSqlite = new UsuariosSqlite(LoginActvity.this, "UsersDB.sqlite", null, 1);
        usuariosSqlite.queryData("CREATE TABLE IF NOT EXISTS USERS(Id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR , codigo INTEGER unique, image VARCHAR, tipo VARCHAR)");

        Cursor cursor = usuariosSqlite.getData("SELECT * FROM USERS");

        SolicitarPermisos();
        if (savedInstanceState == null) {
            if (tipo.length() > 0 || nombre.length() > 0 || codigo != 0 || image.length() > 0 || tipos.length() > 0) {
                Intent intent = new Intent(this, NavigationActivity.class);
                startActivity(intent);
                finish();
                usuariosSqlite.close();
            } else if (cursor.moveToFirst()) {
                Fragment fragment = new LoginForOneTouch();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.contenedor, fragment);
                fragmentTransaction.commit();
                usuariosSqlite.close();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new LoginFragment();
                fragmentTransaction.add(R.id.contenedor, fragment);
                fragmentTransaction.commit();
                usuariosSqlite.close();
            }
        }
        interstitialAd.setAdListener(new AdListener(){
            public void onAdLoaded(){
                if (interstitialAd.isLoaded()) {
                    //interstitialAd.show();
                }
            }
        });
    }



    private void SolicitarPermisos() {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_RW_EXTERNAL_STORAGE);

            }
        }*/
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int call = checkSelfPermission(Manifest.permission.CALL_PHONE);
            int rw = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (call != PackageManager.PERMISSION_GRANTED &&
                    rw != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(perms, permsRequestCode);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().commit();
        } else {
            super.onBackPressed();
        }
    }
}
