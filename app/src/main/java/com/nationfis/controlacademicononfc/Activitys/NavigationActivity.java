package com.nationfis.controlacademicononfc.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nationfis.controlacademicononfc.Fragments.ActivarEstudiantes;
import com.nationfis.controlacademicononfc.Fragments.ActivarMatriculaFragment;
import com.nationfis.controlacademicononfc.Fragments.AsignaturasFragment;
import com.nationfis.controlacademicononfc.Fragments.ComprobarAsistenciaFragment;
import com.nationfis.controlacademicononfc.Fragments.ComprobarNotasFragment;
import com.nationfis.controlacademicononfc.Fragments.HorariosFragment;
import com.nationfis.controlacademicononfc.Fragments.MainFragment;
import com.nationfis.controlacademicononfc.Fragments.MatriculaAFragment;
import com.nationfis.controlacademicononfc.Fragments.MatriculaFragment;
import com.nationfis.controlacademicononfc.Fragments.MostrarAsistenciaFragment;
import com.nationfis.controlacademicononfc.Fragments.MostrarMatriculadosAsignaturaFragment;
import com.nationfis.controlacademicononfc.Fragments.MostrarNotasFragment;
import com.nationfis.controlacademicononfc.Fragments.MostrarValoraciones;
import com.nationfis.controlacademicononfc.Fragments.RegistrarAsignaturaFragment;
import com.nationfis.controlacademicononfc.Fragments.RegistrarAsignaturasDocentesFragments;
import com.nationfis.controlacademicononfc.Fragments.RegistrarSemestreFragment;
import com.nationfis.controlacademicononfc.Fragments.RegistrarValoracionesFragment;
import com.nationfis.controlacademicononfc.R;


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String tipo,nombre,image,tipos,sede,epn;
    private Bitmap foto;
    //public static final String urla = "https://nationfis.000webhostapp.com/controlacademico/entrada.php";
    public static final String urla = "http://192.168.1.38/controlacademico/entrada.php";
    //public static String urla1 = "https://nationfis.000webhostapp.com/controlacademico/reportes/asistencia.php";
    public static String urla1 = "http://192.168.1.38/controlacademico/reportes/asistencia.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new MainFragment();
        fragmentTransaction.add(R.id.contenedorn,fragment);
        fragmentTransaction.commit();

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tipo = preferences.getString("a","");
        nombre = preferences.getString("nombre","");
        image = preferences.getString("image","");
        tipos = preferences.getString("tipos","");
        sede = preferences.getString("seden","");
        epn = preferences.getString("epn","");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        switch (tipo) {
            case "ac99fecf6fcb8c25d18788d14a5384ee":
                navigationView.getMenu().setGroupVisible(R.id.group1, true);
                break;
            case "fd09accffacf03d7393c2a23a9601b43":
                navigationView.getMenu().setGroupVisible(R.id.group2, true);
                break;
            case "e4e4564027d73a4325024d948d167e93":
                navigationView.getMenu().setGroupVisible(R.id.group3, true);
                break;
            default:
                break;
        }

        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        TextView nombret = (TextView)view.findViewById(R.id.nombre);
        TextView ep = (TextView) view.findViewById(R.id.ep);
        TextView tipot = (TextView) view.findViewById(R.id.tipo);
        TextView sedet = (TextView) view.findViewById(R.id.sede);
        ImageView fotot = (ImageView) view.findViewById(R.id.foto);

        nombret.setText(nombre);
        tipot.setText(tipos);
        sedet.setText(sede);
        ep.setText(epn);
        byte[] byteImage = Base64.decode(image, Base64.DEFAULT);
        foto = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
        fotot.setImageBitmap(foto);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().commit();
            }
            else {
                super.onBackPressed();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*String nivel= loginLogin.getNivel();
        switch (nivel){
            case "estudiante":
                break;
            case "docente":
                getMenuInflater().inflate(R.menu.navigation, menu);
                break;
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.asismanual:
                fragment = new ComprobarAsistenciaFragment();
                fragmentTransaction = true;
                break;
            case R.id.notasmanual:
                fragment = new ComprobarNotasFragment();
                fragmentTransaction = true;
                break;
        }
        if (fragmentTransaction){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedorn,fragment).commit();
            setTitle(item.getTitle());
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean fragmentTransaction = false;
        Fragment fragment = null;
        switch (id){
            case R.id.llamar:
                fragment = new ComprobarAsistenciaFragment();
                fragmentTransaction = true;
                break;
            case R.id.registrar:
                fragment = new ComprobarNotasFragment();
                fragmentTransaction = true;
                break;
            case R.id.asisten:
                fragment = new MostrarAsistenciaFragment();
                fragmentTransaction = true;
                break;
            case R.id.nota:
                fragment = new MostrarNotasFragment();
                fragmentTransaction = true;
                break;
            case R.id.matria:
                fragment = new MatriculaAFragment();
                fragmentTransaction = true;
                break;
            case R.id.matrin:
                fragment = new MatriculaFragment();
                fragmentTransaction = true;
                break;
            case R.id.actest:
                fragment = new ActivarEstudiantes();
                fragmentTransaction = true;
                break;
            case R.id.actmat:
                fragment = new ActivarMatriculaFragment();
                fragmentTransaction =true;
                break;
            case R.id.regasig:
                fragment = new RegistrarAsignaturaFragment();
                fragmentTransaction = true;
                break;
            case R.id.regasigd:
                fragment = new RegistrarAsignaturasDocentesFragments();
                fragmentTransaction = true;
                break;
            case R.id.regsemest:
                fragment = new RegistrarSemestreFragment();
                fragmentTransaction = true;
                break;
            case R.id.mostrarasistencia:
                fragment = new MostrarAsistenciaFragment();
                fragmentTransaction = true;
                break;
            case R.id.mostrarnota:
                fragment = new MostrarNotasFragment();
                fragmentTransaction = true;
                break;
            case R.id.asignaturas:
                fragment = new AsignaturasFragment();
                fragmentTransaction = true;
                break;
            case R.id.matr:
                fragment = new MostrarMatriculadosAsignaturaFragment();
                fragmentTransaction = true;
                break;
            case R.id.horario:
                fragment = new HorariosFragment();
                fragmentTransaction = true;
                break;
            case R.id.regval:
                fragment = new RegistrarValoracionesFragment();
                fragmentTransaction = true;
                break;
            case R.id.valo:
                fragment = new MostrarValoraciones();
                fragmentTransaction = true;
                break;
            case R.id.contacto:
                break;
            case R.id.cerrar:
                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(NavigationActivity.this,LoginActvity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        if(fragmentTransaction){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedorn,fragment).commit();
            item.setChecked(true);
            //getSupportActionBar().setTitle(item.getTitle());
            setTitle(item.getTitle());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
