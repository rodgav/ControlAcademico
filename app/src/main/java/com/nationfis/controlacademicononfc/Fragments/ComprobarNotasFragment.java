package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.EnviarNotas.RegistrarNota;
import com.nationfis.controlacademicononfc.Views.ComprobarNotas.ComprobarNotas;
import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes.RecibirAsignaturasDocentes;
import com.nationfis.controlacademicononfc.Clases.Spinners.Unidad.RecibirUnidades;
import com.nationfis.controlacademicononfc.R;

import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComprobarNotasFragment extends Fragment implements View.OnClickListener{


    public ComprobarNotasFragment() {
        // Required empty public constructor
    }

    private RecyclerView notas;
    private Integer codigo;
    private DatosDatos datosDatos = new DatosDatos();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comprobar_notas, container, false);
        Spinner asignaturas =  view.findViewById(R.id.asignaturas);
        Spinner unidades =  view.findViewById(R.id.unidades);
        Button registrar =  view.findViewById(R.id.registrar);
        notas = view.findViewById(R.id.notas);

        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("datos", Context.MODE_PRIVATE);
        codigo = preferences.getInt("codigo",0);
        String accion= MD5.encrypt("unidades");

        notas.setLayoutManager(new LinearLayoutManager(getActivity()));

        new RecibirAsignaturasDocentes(getActivity(),urla,codigo, asignaturas).execute();
        new RecibirUnidades(getActivity(),urla, unidades,accion).execute();

        registrar.setOnClickListener(ComprobarNotasFragment.this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registrar:
                notas.setAdapter(null);
                registra();
                break;
        }
    }

    private void llenar() {
        Integer codiasi= datosDatos.getAsignaturasd();
        Integer codiuni= datosDatos.getUnidades();
        String tipo = "doc";
        String accion= MD5.encrypt("mnotas");
        String anioa = Objects.requireNonNull(getActivity()).getResources().getString(R.string.año);
        new ComprobarNotas(getActivity(),urla,accion,notas,codiuni,codiasi,tipo,codigo,anioa).execute();
    }

    private void registra() {
        String accion1 = MD5.encrypt("notaa");
        String accion2 = MD5.encrypt("notan");
        Integer codiasi= datosDatos.getAsignaturasd();
        Integer codiuni= datosDatos.getUnidades();
        String anioa = Objects.requireNonNull(getActivity()).getResources().getString(R.string.año);
        RegistrarNota registrarNota =new RegistrarNota(getActivity(),urla,accion1,codiasi,codiuni,codigo,anioa);
        registrarNota.execute();
        RegistrarNota registrarNota1 =new RegistrarNota(getActivity(),urla,accion2,codiasi,codiuni,codigo,anioa);
        registrarNota1.execute();
        llenar();
    }
}
