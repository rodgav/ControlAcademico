package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.nationfis.controlacademicononfc.R;
import com.nationfis.controlacademicononfc.Views.MostrarMatriculados.SpinnerAsignaturas.RecibirASP1;

import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarMatriculadosAsignaturaFragment extends Fragment {


    public MostrarMatriculadosAsignaturaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_mostrar_matriculados_asignatura, container, false);

        RecyclerView estudiantes = view.findViewById(R.id.estudiantes);
        RecyclerView estudiantesa = view.findViewById(R.id.estudiantesa);
        Spinner asignaturas = view.findViewById(R.id.asignaturas);
        //String urla="http://nationfis.hol.es/nonfc/asignaturad.php";

        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("datos", Context.MODE_PRIVATE);
        Integer codigo = preferences.getInt("codigo", 0);


        estudiantes.setLayoutManager(new LinearLayoutManager(getActivity()));
        estudiantes.setNestedScrollingEnabled(false);
        estudiantesa.setLayoutManager(new LinearLayoutManager(getActivity()));
        estudiantesa.setNestedScrollingEnabled(false);
        new RecibirASP1(getActivity(), urla, codigo, asignaturas, estudiantes, estudiantesa).execute();
        return view;
    }

}
