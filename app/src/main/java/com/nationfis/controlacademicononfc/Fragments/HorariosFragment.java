package com.nationfis.controlacademicononfc.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.R;
import com.nationfis.controlacademicononfc.Views.MostrarHorarios.RecibirHorarios;
import com.nationfis.controlacademicononfc.Views.MostrarHorarios.SpinnerSemestre.RecibirSP;

import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorariosFragment extends Fragment implements View.OnClickListener {


    public HorariosFragment() {
        // Required empty public constructor
    }

    private String accion;
    private String sede, anioa, id;
    private TextView normal, cargo;
    private TableLayout horario;

    @SuppressLint("RtlHardcoded")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        if (getArguments() != null) {
            accion = getArguments().getString("accion", "");
        }

        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        String ep = preferences.getString("ep", "");
        sede = preferences.getString("sede", "");
        id = preferences.getString("codigo", "");
        anioa = getResources().getString(R.string.año);


        if (Objects.equals(accion, "mhor")) {

            view = inflater.inflate(R.layout.fragment_horarios, container, false);

            Spinner semestre = view.findViewById(R.id.semestre);
            horario = view.findViewById(R.id.tabla);

            new RecibirSP(getActivity(),urla,ep,semestre,horario).execute();

        } else if (Objects.equals(accion, "mosmihor")) {
            view = inflater.inflate(R.layout.fragment_mostrar_mi_horario_doc, container, false);
            horario = view.findViewById(R.id.tabla);
            MostrarMiHorariod();

        } else {
            view = inflater.inflate(R.layout.fragment_mi_horario_est, container, false);
            normal = view.findViewById(R.id.textView42);
            cargo = view.findViewById(R.id.textView43);
            horario = view.findViewById(R.id.tabla);

            normal.setOnClickListener(this);
            cargo.setOnClickListener(this);
        }

        return view;
    }

    private void MostrarMiHorarioe(String accion) {
        new RecibirHorarios(getActivity(), urla, accion, id, sede, anioa,horario).execute();
    }

    private void MostrarMiHorariod() {
        new RecibirHorarios(getActivity(), urla, MD5.encrypt(accion), id, sede, anioa,horario).execute();
    }

    private void CambiarColor(TextView color) {
        color.setTextColor(ContextCompat.getColor(getActivity(), R.color.blanco));
        color.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.azul));
    }

    private void RestaurarColor() {
        cargo.setTextColor(ContextCompat.getColor(getActivity(), R.color.negro));
        cargo.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blanco));
        normal.setTextColor(ContextCompat.getColor(getActivity(), R.color.negro));
        normal.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blanco));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView42:
                RestaurarColor();
                CambiarColor(normal);
                horario.removeAllViews();
                String accion = MD5.encrypt("mmihorn");
                MostrarMiHorarioe(accion);
                break;
            case R.id.textView43:
                RestaurarColor();
                CambiarColor(cargo);
                horario.removeAllViews();
                String accion1 = MD5.encrypt("mmihora");
                MostrarMiHorarioe(accion1);
                break;
        }
    }
}
