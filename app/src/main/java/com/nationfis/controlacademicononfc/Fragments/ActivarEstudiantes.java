package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Views.MostrarEstudiantes.MostrarEstudiantes;
import com.nationfis.controlacademicononfc.R;

import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivarEstudiantes extends Fragment implements SearchView.OnQueryTextListener,SwipeRefreshLayout.OnRefreshListener{


    public ActivarEstudiantes() {
        // Required empty public constructor
    }

    private RecyclerView estudiantes;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String accion,anioa;
    private Integer ep,sede;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activar_estudiantes, container, false);

        if (getArguments()!=null){
            accion = MD5.encrypt(getArguments().getString("accion", "accion"));
        }

        estudiantes = view.findViewById(R.id.estudiantes);
        SearchView searchView =  view.findViewById(R.id.sv);
        anioa = getResources().getString(R.string.año);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("datos", Context.MODE_PRIVATE);
        ep = preferences.getInt("ep",0);
        sede = preferences.getInt("sede",0);

        searchView.setOnQueryTextListener(ActivarEstudiantes.this);
        swipeRefreshLayout.setOnRefreshListener(ActivarEstudiantes.this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                descargar("");
            }
        });

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        descargar(s);
        return false;
    }

    private void descargar(String s) {
        estudiantes.setAdapter(null);
        swipeRefreshLayout.setRefreshing(true);
        estudiantes.setLayoutManager(new LinearLayoutManager(getActivity()));
        new MostrarEstudiantes(getActivity(),urla,accion,s,estudiantes,ep,anioa,sede,swipeRefreshLayout).execute();
    }

    @Override
    public boolean onQueryTextChange(String s) {
        descargar(s);
        return false;
    }

    @Override
    public void onRefresh() {
        descargar("");
    }
}
