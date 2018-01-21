package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.ListViews.MostrarValoraciones.MostrarValoracionesL;
import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes.RecibirAsignaturasDocentes;
import com.nationfis.controlacademicononfc.Clases.Spinners.Unidad.RecibirUnidades;
import com.nationfis.controlacademicononfc.R;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarValoraciones extends Fragment implements View.OnClickListener{


    public MostrarValoraciones() {
        // Required empty public constructor
    }

    private ListView valores;
    private String codigo;
    private DatosDatos datosDatos = new DatosDatos();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mostrar_valoraciones, container, false);

        Spinner asignaturas =  view.findViewById(R.id.asignaturas);
        Spinner unidades =  view.findViewById(R.id.unidades);
        Button registrar =  view.findViewById(R.id.registrar);
        valores = view.findViewById(R.id.valores);
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        codigo = preferences.getString("codigo","");
        String sede = preferences.getString("sede", "");
        String anioa = getResources().getString(R.string.año);
        String accion=MD5.encrypt("unidades");
        String tipo="solo";

        new RecibirAsignaturasDocentes(getActivity(),urla,codigo, asignaturas,valores,valores,tipo, sede, anioa).execute();
        new RecibirUnidades(getActivity(),urla, unidades,accion).execute();
        registrar.setOnClickListener(MostrarValoraciones.this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registrar:
                valores.setAdapter(null);
                registra();
                break;
        }
    }

    private void registra() {
        String codiasi= datosDatos.getAsignaturasd();
        String codiuni= datosDatos.getUnidades();
        String accion = MD5.encrypt("mostrarvalolv");
        MostrarValoracionesL mo = new MostrarValoracionesL(getActivity(),urla,accion,codiasi,codiuni,valores,codigo);
        mo.execute();
    }
}
