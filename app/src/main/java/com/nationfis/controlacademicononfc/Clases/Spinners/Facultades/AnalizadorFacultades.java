package com.nationfis.controlacademicononfc.Clases.Spinners.Facultades;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.Spinners.Escuelas.RecibirEscuelas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/*
 * Created by SamGM on 22/04/2017.
 */

public class AnalizadorFacultades extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s,matricula1;
    @SuppressLint("StaticFieldLeak")
    private Spinner facultad,escuela,semestre,asignatura;
    private ArrayList<String>nof = new ArrayList<>();
    private ArrayList<Integer>idf = new ArrayList<>();
    AnalizadorFacultades(Context c, String s, Spinner facultad, Spinner escuela, Spinner semestre, Spinner asignatura, String matricula1) {
        this.c = c;
        this.s = s;
        this.facultad = facultad;
        this.escuela = escuela;
        this.semestre = semestre;
        this.matricula1 = matricula1;
        this.asignatura = asignatura;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try{
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            nof.clear();
            idf.clear();
            for (int i=0;i<ja.length();i++){
                jo = ja.getJSONObject(i);
                Integer id = jo.getInt("codigo");
                String nombre = jo.getString("nombre");

                nof.add(nombre);
                idf.add(id);
            }
            return 1;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        //final String urla = "http://nationfis.hol.es/nonfc/escuela.php";
        if (integer==0){
            Toast.makeText(c,"No se pudo analizar los datos",Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> a = new ArrayAdapter<>(c,android.R.layout.simple_expandable_list_item_1,nof);
            facultad.setAdapter(a);

            facultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                    //Toast.makeText(c,nof.get(i),Toast.LENGTH_SHORT).show();
                    DatosDatos datosDatos = new DatosDatos();
                    datosDatos.setFacultades(idf.get(i));
                    new RecibirEscuelas(c,urla,idf.get(i),escuela,semestre,asignatura,matricula1).execute();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
}
