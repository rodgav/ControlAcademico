package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.R;
import com.nationfis.controlacademicononfc.Views.MostrarAllNotas.RecibirNotas;
import com.nationfis.controlacademicononfc.Views.MostrarAllNotas.SpinnerAsignaturas.RecibirASP;

import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;
import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarAllNotas extends Fragment {


    public MostrarAllNotas() {
        // Required empty public constructor
    }

    private String accion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        if (getArguments()!=null){
            accion = getArguments().getString("accion","");
        }

        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);

        Integer codigo;
        TableLayout notas;

        if (Objects.equals(accion,"mnotad")){
            view = inflater.inflate(R.layout.fragment_mostrar_all_notas, container, false);
            Spinner asignaturas = view.findViewById(R.id.asignaturas);
            notas = view.findViewById(R.id.tabla);

            codigo = preferences.getInt("codigo",0);

            new RecibirASP(getActivity(),urla, codigo,asignaturas, notas).execute();
        }else if (Objects.equals(accion,"mnotae")){
            view = inflater.inflate(R.layout.fragment_mostrar_all_notas_est, container, false);
            notas = view.findViewById(R.id.tabla);

            codigo = preferences.getInt("codigo",0);
            String anioa = getActivity().getResources().getString(R.string.año);
            Log.w(TAG,accion+anioa+codigo);
            new RecibirNotas(getActivity(),urla, MD5.encrypt(accion),anioa, codigo, notas).execute();
        }
        return view;
    }
}
