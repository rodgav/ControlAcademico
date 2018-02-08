package com.nationfis.controlacademicononfc.Clases.EliminarHorario;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import com.nationfis.controlacademicononfc.Views.MostrarEstudiantes.Estudiantes;

import java.util.ArrayList;

/*
 * Created by GlobalTIC's on 7/02/2018.
 */

public class EliminarHorario extends AsyncTask<Void,Void,String> {
    public EliminarHorario(Context c, String urla, String accion, String codigoa, String idd, String sede, String inicio, String fin, String anioa, Dialog d, ArrayList<Estudiantes> estudiantes, int adapterPosition) {
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.analizar();
    }

    private String analizar() {
        return null;
    }
}
