package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.ListViews.MostrarAsignaturas.MostrarAsignaturas;
import com.nationfis.controlacademicononfc.R;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsignaturasFragment extends Fragment {


    public AsignaturasFragment() {
        // Required empty public constructor
    }

    private ListView estudiantes,estudiantesa;
    private String codigo,anioa;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_asignaturas, container, false);
        estudiantes = view.findViewById(R.id.estudiantes);
        estudiantesa = view.findViewById(R.id.estudiantesa);

        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        codigo = preferences.getString("codigo","");
        anioa = getResources().getString(R.string.año);

        llenar();
        return view;
    }

    private void llenar() {
        String accion0="asignaturasa";
        String accion = MD5.encrypt(accion0);
        new MostrarAsignaturas(getActivity(),urla,accion,codigo,estudiantesa,anioa).execute();
        llenar1();
    }

    private void llenar1() {
        String accion0="asignaturasn";
        String accion = MD5.encrypt(accion0);
        new MostrarAsignaturas(getActivity(),urla,accion,codigo,estudiantes,anioa).execute();
    }
}
