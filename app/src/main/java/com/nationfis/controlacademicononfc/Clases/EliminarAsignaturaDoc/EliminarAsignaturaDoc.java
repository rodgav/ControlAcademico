package com.nationfis.controlacademicononfc.Clases.EliminarAsignaturaDoc;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import com.nationfis.controlacademicononfc.Views.MostrarEstudiantes.AdaptadorEstudiantes;
import com.nationfis.controlacademicononfc.Views.MostrarEstudiantes.Estudiantes;

import java.util.ArrayList;

/**
 * Created by GlobalTIC's on 7/02/2018.
 */

public class EliminarAsignaturaDoc extends AsyncTask<Void,Void,String>{
    private Context c;
    private String urla,accion,codigo,codigoa,sede,anioa;
    private Dialog d;
    private ArrayList<Estudiantes>estudiantes;
    private int adapterPosition;

    public EliminarAsignaturaDoc(Context c, String urla, String accion, String codigo, String codigoa, String sede, String anioa, Dialog d, ArrayList<Estudiantes> estudiantes,int adapterPosition ) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.codigo = codigo;
        this.codigoa = codigoa;
        this.sede = sede;
        this.anioa = anioa;
        this.d = d;
        this.estudiantes = estudiantes;
        this.adapterPosition = adapterPosition;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.eliminar();
    }

    private String eliminar() {
        estudiantes.remove(adapterPosition);

        return null;
    }
}
