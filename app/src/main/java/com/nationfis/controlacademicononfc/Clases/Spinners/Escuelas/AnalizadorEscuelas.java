package com.nationfis.controlacademicononfc.Clases.Spinners.Escuelas;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.Spinners.Semestres.RecibirSemestres;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * Created by SamGM on 22/04/2017.
 */

public class AnalizadorEscuelas extends AsyncTask<Void,Void,Integer>{
    private ArrayList<String>ide = new ArrayList<>();
    private ArrayList<String>noe = new ArrayList<>();
    private Context c;
    private String s,matricula1;
    private Spinner escuela,semestre,asignatura;
    private DatosDatos datosDatos;
    public AnalizadorEscuelas(Context c, Spinner escuela, Spinner semestre,Spinner asignatura,String s,String matricula1) {
        this.c = c;
        this.escuela = escuela;
        this.semestre = semestre;
        this.asignatura = asignatura;
        this.s = s;
        this.matricula1 = matricula1;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            ide.clear();
            noe.clear();
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
             for (int i =0; i<ja.length();i++){
                 jo=ja.getJSONObject(i);
                 String nombre = jo.getString("nombre");
                 String codigo = jo.getString("codigo");
                 ide.add(codigo);
                 noe.add(nombre);
             }
             return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        //final String urla = "http://nationfis.hol.es/nonfc/semestre.php";
        if (integer == 0){
            Toast.makeText(c,"No se analizaron los datos",Toast.LENGTH_SHORT).show();
        }else {
            datosDatos = new DatosDatos();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,noe);
            escuela.setAdapter(adapter);

            escuela.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (Objects.equals(matricula1,"regsemest")){
                        Toast.makeText(c,noe.get(i),Toast.LENGTH_SHORT).show();
                        String var = ide.get(i);
                        datosDatos.setEscuel(var);
                    }else {
                        Toast.makeText(c,noe.get(i),Toast.LENGTH_SHORT).show();
                        String var = ide.get(i);
                        datosDatos.setEscuel(var);
                        new RecibirSemestres(c,urla,var,semestre,asignatura,matricula1).execute();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
}
