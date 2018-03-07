package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Views.ComprobarNotas.ComprobarNotas;
import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes.RecibirAsignaturasDocentes;
import com.nationfis.controlacademicononfc.Clases.Spinners.Unidad.RecibirUnidades;
import com.nationfis.controlacademicononfc.R;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarNotasFragment extends Fragment implements View.OnClickListener {


    public MostrarNotasFragment() {
        // Required empty public constructor
    }

    private RecyclerView notas;
    private Integer codigo;
    private DatosDatos datosDatos = new DatosDatos();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        codigo = preferences.getInt("codigo", 0);
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mostrar_notas, container, false);
        Spinner asignaturas = view.findViewById(R.id.asignaturas);
        Spinner unidades = view.findViewById(R.id.unidades);
        Button registrar = view.findViewById(R.id.registrar);
        notas = view.findViewById(R.id.notas);

        String accion = MD5.encrypt("unidades");
        notas.setLayoutManager(new LinearLayoutManager(getActivity()));
        new RecibirAsignaturasDocentes(getActivity(), urla, codigo, asignaturas).execute();
        new RecibirUnidades(getActivity(), urla, unidades, accion).execute();

        registrar.setOnClickListener(MostrarNotasFragment.this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registrar:
                String tipo = "doc";
                String accion = MD5.encrypt("mnotas");
                Integer codiasi = datosDatos.getAsignaturasd();
                llenar(tipo, accion, codiasi);
                break;
        }
    }

    private void llenar(String tipo, String accion, Integer codiasi) {
        Integer codiuni = datosDatos.getUnidades();
        String anioa = getActivity().getResources().getString(R.string.año);
        new ComprobarNotas(getActivity(), urla, accion, notas, codiuni, codiasi, tipo, codigo, anioa).execute();
    }
}
