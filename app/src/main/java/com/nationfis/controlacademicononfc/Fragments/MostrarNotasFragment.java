package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarNotas.ComprobarNotas;
import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes.RecibirAsignaturasDocentes;
import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasEstudiantes.RecibirAsignaturasEstudiantes;
import com.nationfis.controlacademicononfc.Clases.Spinners.Unidad.RecibirUnidades;
import com.nationfis.controlacademicononfc.Clases.Spinners.Valoraciones.RecibirValoraciones;
import com.nationfis.controlacademicononfc.R;

import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarNotasFragment extends Fragment implements View.OnClickListener{


    public MostrarNotasFragment() {
        // Required empty public constructor
    }
    private Spinner asignaturas,unidades,valores;
    private Button registrar,normal,anormal;
    private ImageButton cargarva;
    private ListView notas;
    private TextView textView19;
    private String nivel,codigo,sede,anioa;
    private DatosDatos datosDatos = new DatosDatos();
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        nivel = preferences.getString("a","");
        codigo = preferences.getString("codigo","");
        sede = preferences.getString("sede","");
        anioa =  getResources().getString(R.string.año);
        // Inflate the layout for this fragment
        if (Objects.equals(nivel,"e4e4564027d73a4325024d948d167e93")){
            view = inflater.inflate(R.layout.fragment_mostrar_notas_estudiante,container,false);
            asignaturas = (Spinner)view.findViewById(R.id.asignaturas);
            unidades = (Spinner)view.findViewById(R.id.unidades);
            registrar = (Button)view.findViewById(R.id.registrar);
            normal = (Button)view.findViewById(R.id.normal);
            anormal = (Button)view.findViewById(R.id.anormal);
            notas = (ListView)view.findViewById(R.id.notas);

            String accion= MD5.encrypt("unidades");
            normal.setOnClickListener(MostrarNotasFragment.this);
            anormal.setOnClickListener(MostrarNotasFragment.this);
            new RecibirUnidades(getActivity(),urla,unidades,accion).execute();

        }else if (Objects.equals(nivel,"ac99fecf6fcb8c25d18788d14a5384ee")){
            view = inflater.inflate(R.layout.fragment_mostrar_notas, container, false);
            asignaturas = (Spinner)view.findViewById(R.id.asignaturas);
            unidades = (Spinner)view.findViewById(R.id.unidades);
            valores = (Spinner)view.findViewById(R.id.valores);
            registrar = (Button)view.findViewById(R.id.registrar);
            notas = (ListView)view.findViewById(R.id.notas);
            cargarva = (ImageButton)view.findViewById(R.id.cargarva);
            textView19 = (TextView)view.findViewById(R.id.textView19);


                String accion=MD5.encrypt("unidades");
                String tipo="solo";
                cargarva.setOnClickListener(MostrarNotasFragment.this);
                new RecibirAsignaturasDocentes(getActivity(),urla,codigo,asignaturas,notas,notas,tipo,sede,anioa).execute();
                new RecibirUnidades(getActivity(),urla,unidades,accion).execute();
        }
        registrar.setOnClickListener(MostrarNotasFragment.this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.normal:
                asignaturas.setAdapter(null);
                normales();
                break;
            case R.id.anormal:
                asignaturas.setAdapter(null);
                anormales();
                break;
            case R.id.cargarva:
                notas.setAdapter(null);
                cargarspinner();
                break;
            case R.id.registrar:
                if (Objects.equals(nivel,"e4e4564027d73a4325024d948d167e93")){
                    String tipo = "est";
                    llenare(tipo);
                }else if (Objects.equals(nivel,"ac99fecf6fcb8c25d18788d14a5384ee")){
                    String tipo = "doc";
                    llenard(tipo);
                }
                break;
        }
    }
    private void llenare(String tipo) {
        String codiasi= datosDatos.getAsignaturase();
        String codiuni= datosDatos.getUnidades();
        String codival = datosDatos.getValoraciones();
        new ComprobarNotas(getActivity(),urla,notas,codiuni,codiasi,codival,tipo,codigo).execute();
    }
    private void llenard(String tipo) {
        String codiasi= datosDatos.getAsignaturasd();
        String codiuni= datosDatos.getUnidades();
        String codival = datosDatos.getValoraciones();
        new ComprobarNotas(getActivity(),urla,notas,codiuni,codiasi,codival,tipo,codigo).execute();
    }

    private void normales() {
        String accion = MD5.encrypt("asignaturasn");
        new RecibirAsignaturasEstudiantes(getActivity(),urla,codigo,accion,asignaturas).execute();
    }
    private void anormales() {
        String accion = MD5.encrypt("asignaturasa");
        new RecibirAsignaturasEstudiantes(getActivity(),urla,codigo,accion,asignaturas).execute();
    }

    private void cargarspinner() {
        String codiasi= datosDatos.getAsignaturasd();
        String codiuni= datosDatos.getUnidades();
        String accion = MD5.encrypt("mostrarvalosp");
        new RecibirValoraciones(getActivity(),urla,codiasi,codiuni,accion,valores,codigo).execute();
    }
}
