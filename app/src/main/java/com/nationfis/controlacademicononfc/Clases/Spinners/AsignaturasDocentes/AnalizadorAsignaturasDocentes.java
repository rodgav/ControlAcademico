package com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.ListViews.MostrarMatriculados.EnviarAsistencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/*
 * Created by SamGM on 22/04/2017.
 */

public class AnalizadorAsignaturasDocentes extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s, tipo,sede,anioa;
    @SuppressLint("StaticFieldLeak")
    private Spinner asignaturas;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView estudiantes, estudiantesa;
    private ArrayList<String> noa = new ArrayList<>();
    private ArrayList<String> ida = new ArrayList<>();
    private DatosDatos datosDatos = new DatosDatos();
    AnalizadorAsignaturasDocentes(Context c, String s, Spinner asignaturas, RecyclerView estudiantes, RecyclerView estudiantesa,
                                  String tipo, String sede, String anioa) {
        this.c = c;
        this.s = s;
        this.asignaturas = asignaturas;
        this.estudiantes = estudiantes;
        this.estudiantesa = estudiantesa;
        this.tipo = tipo;
        this.sede = sede;
        this.anioa = anioa;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer ==0){
            Toast.makeText(c,"Nose pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(c,android.R.layout.simple_list_item_1,noa);
            asignaturas.setAdapter(adapter);
            asignaturas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(c,ida.get(i),Toast.LENGTH_SHORT).show();
                    String asignaturasd = ida.get(i);
                    datosDatos.setAsignaturasd(asignaturasd);

                    if (Objects.equals(tipo,"manual")){
                        estudiantes.setAdapter(null);
                        estudiantesa.setAdapter(null);
                        llenarestudiante1(i);
                    }else if(Objects.equals(tipo,"comprobar")){
                        estudiantes.setAdapter(null);
                        estudiantesa.setAdapter(null);
                        //pasarasistencia(i,fecha);
                        //llenarestudiante(i,fecha);
                    }
                }

                /*private void pasarasistencia(int i,String fecha) {
                    loginLogin = new LoginLogin();
                    String codigod = loginLogin.getCodigo();
                    String urla3 = "http://nationfis.hol.es/nonfc/casisanor.php";
                    String urla4 = "http://nationfis.hol.es/nonfc/casisnorm.php";
                    RegistrarAsistencia registrarAsistencia = new RegistrarAsistencia(c,urla3,ida.get(i),codigod,fecha);
                    registrarAsistencia.execute();

                    RegistrarAsistencia registrarAsistencia1 = new RegistrarAsistencia(c,urla4,ida.get(i),codigod,fecha);
                    registrarAsistencia1.execute();

                }

                private void llenarestudiante(int i,String fecha) {
                    String urla2 = "http://nationfis.hol.es/nonfc/asistidos1.php";
                    String tipo1 = "pasar";
                    ComprobarAsistencia comprobarAsistencia = new ComprobarAsistencia(c,urla2,ida.get(i),fecha,estudiantes,tipo1);
                    comprobarAsistencia.execute();
                }*/

                private void llenarestudiante1(int i) {
                    //String urla = "http://nationfis.hol.es/nonfc/estudiantesn.php";
                    //String urla1 = "http://nationfis.hol.es/nonfc/estudiantesa.php";
                    String accion1 = MD5.encrypt("estudiantesn");
                    String accion2 = MD5.encrypt("estudiantesa");
                    EnviarAsistencia enviarAsistencia = new EnviarAsistencia(c,urla,ida.get(i),estudiantes,accion1,sede,anioa);
                    enviarAsistencia.execute();

                    EnviarAsistencia enviarAsistencia1 = new EnviarAsistencia(c,urla,ida.get(i),estudiantesa,accion2,sede,anioa);
                    enviarAsistencia1.execute();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            noa.clear();
            ida.clear();
            for (int i = 0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String codigo=jo.getString("codigo");
                String nombre = jo.getString("nombre");
                noa.add(nombre);
                ida.add(codigo);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
