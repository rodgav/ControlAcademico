package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.EnviarNotas.RegistrarNota;
import com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarNotas.ComprobarNotas;
import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes.RecibirAsignaturasDocentes;
import com.nationfis.controlacademicononfc.Clases.Spinners.Unidad.RecibirUnidades;
import com.nationfis.controlacademicononfc.Clases.Spinners.Valoraciones.RecibirValoraciones;
import com.nationfis.controlacademicononfc.R;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComprobarNotasFragment extends Fragment implements View.OnClickListener{


    public ComprobarNotasFragment() {
        // Required empty public constructor
    }

    private Spinner valores;
    private RecyclerView notas;
    private String codigo;
    private DatosDatos datosDatos = new DatosDatos();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comprobar_notas, container, false);
        Spinner asignaturas =  view.findViewById(R.id.asignaturas);
        Spinner unidades =  view.findViewById(R.id.unidades);
        valores = view.findViewById(R.id.valores);
        ImageButton cargarva =  view.findViewById(R.id.cargarva);
        Button registrar =  view.findViewById(R.id.registrar);
        notas = view.findViewById(R.id.notas);

        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        codigo = preferences.getString("codigo","");
        String sede = preferences.getString("sede", "");
        String anioa = getResources().getString(R.string.año);
        String accion= MD5.encrypt("unidades");
        String tipo="solo";

        new RecibirAsignaturasDocentes(getActivity(),urla,codigo, asignaturas,notas,notas,tipo, sede, anioa).execute();
        new RecibirUnidades(getActivity(),urla, unidades,accion).execute();

        cargarva.setOnClickListener(ComprobarNotasFragment.this);
        registrar.setOnClickListener(ComprobarNotasFragment.this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cargarva:
                notas.setAdapter(null);
                cargarspinner();
                break;
            case R.id.registrar:
                notas.setAdapter(null);
                registra();
                break;
        }
    }

    private void llenar() {
        String codiasi= datosDatos.getAsignaturasd();
        String codiuni= datosDatos.getUnidades();
        String codival = datosDatos.getValoraciones();
        String tipo = "doc";
        //new ComprobarNotas(getActivity(),urla,notas,codiuni,codiasi,codival,tipo,codigo).execute();
    }

    private void registra() {
        String accion1 = MD5.encrypt("notaa");
        String accion2 = MD5.encrypt("notan");
        String codiasi= datosDatos.getAsignaturasd();
        String codiuni= datosDatos.getUnidades();
        String codival = datosDatos.getValoraciones();
        RegistrarNota registrarNota =new RegistrarNota(getActivity(),urla,accion1,codiasi,codiuni,codival,codigo);
        registrarNota.execute();
        RegistrarNota registrarNota1 =new RegistrarNota(getActivity(),urla,accion2,codiasi,codiuni,codival,codigo);
        registrarNota1.execute();
        llenar();
    }

    private void cargarspinner() {
        String codiasi= datosDatos.getAsignaturasd();
        String codiuni= datosDatos.getUnidades();
        String accion = MD5.encrypt("mostrarvalosp");
        new RecibirValoraciones(getActivity(),urla,codiasi,codiuni,accion,valores,codigo).execute();
    }
}
