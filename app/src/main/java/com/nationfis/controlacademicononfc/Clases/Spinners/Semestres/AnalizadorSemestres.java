package com.nationfis.controlacademicononfc.Clases.Spinners.Semestres;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.Spinners.Asignaturas.RecibirAsignaturas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/*
 * Created by SamGM on 22/04/2017.
 */

public class AnalizadorSemestres extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    @SuppressLint("StaticFieldLeak")
    private Spinner semestre,asignatura;
    private String s,matricula1;
    private ArrayList<String>nos = new ArrayList<>();
    private ArrayList<String>ids = new ArrayList<>();
    private DatosDatos datosDatos;
    AnalizadorSemestres(Context c, String s, Spinner semestre, Spinner asignatura, String matricula1) {
        this.c = c;
        this.s = s;
        this.semestre = semestre;
        this.asignatura = asignatura;
        this.matricula1 = matricula1;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {

        try {
            nos.clear();
            ids.clear();
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            for (int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                String codigo = jo.getString("codigo");
                nos.add(nombre);
                ids.add(codigo);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        //final String urla="http://nationfis.hol.es/nonfc/asignatura.php";
        if (integer==0){
            Toast.makeText(c,"No se analizaron los datos",Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> a = new ArrayAdapter<>(c,android.R.layout.simple_list_item_1,nos);
            semestre.setAdapter(a);
            datosDatos = new DatosDatos();

            semestre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (Objects.equals(matricula1,"matriculaa")){
                        Toast.makeText(c,nos.get(i),Toast.LENGTH_SHORT).show();
                        String var = ids.get(i);
                        new RecibirAsignaturas(c,urla,var,asignatura).execute();
                    }else{
                        Toast.makeText(c,nos.get(i),Toast.LENGTH_SHORT).show();
                        datosDatos.setSemestres(ids.get(i));
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
}
