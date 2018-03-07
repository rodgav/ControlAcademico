package com.nationfis.controlacademicononfc.Views.MostrarHorarios.SpinnerSemestre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.R;
import com.nationfis.controlacademicononfc.Views.MostrarHorarios.RecibirHorarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/*
 * Created by GlobalTIC's on 22/02/2018.
 */

public class AnalizadorSP extends AsyncTask<Void, Void, Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s;
    @SuppressLint("StaticFieldLeak")
    private Spinner semestre;
    private ArrayList<String> nos = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private TableLayout horarios;
    private SharedPreferences preferences;
    AnalizadorSP(Context c, String s, Spinner semestre, TableLayout horarios) {
        this.c = c;
        this.s = s;
        this.semestre = semestre;
        this.horarios = horarios;
        preferences = c.getSharedPreferences("datos",Context.MODE_PRIVATE);
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
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                Integer codigo = jo.getInt("codigo");

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
        if (integer == 0) {
            Toast.makeText(c, "No se cargaron los semestres", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> a = new ArrayAdapter<>(c, android.R.layout.simple_list_item_1, nos);
            semestre.setAdapter(a);
            semestre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(c, nos.get(i), Toast.LENGTH_SHORT).show();
                    Integer var = ids.get(i);
                    Integer sede = preferences.getInt("sede",0);
                    String anioa = c.getResources().getString(R.string.año);
                    horarios.removeAllViews();
                    new RecibirHorarios(c,urla, MD5.encrypt("mhor"),var,sede,anioa,horarios).execute();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
}
