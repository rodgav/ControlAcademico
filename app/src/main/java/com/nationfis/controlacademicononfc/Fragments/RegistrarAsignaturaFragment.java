package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.RegistrarAsignaturas.RegistrarAsignaturas;
import com.nationfis.controlacademicononfc.Clases.Spinners.Facultades.RecibirFacultades;
import com.nationfis.controlacademicononfc.Clases.Spinners.Semestres.RecibirSemestres;
import com.nationfis.controlacademicononfc.R;
import com.nationfis.controlacademicononfc.Views.MostrarHorarios.SpinnerSemestre.RecibirSP;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarAsignaturaFragment extends Fragment implements View.OnClickListener {


    public RegistrarAsignaturaFragment() {
        // Required empty public constructor
    }
    private EditText nombre;
    //private String urla = "http://nationfis.hol.es/nonfc/facultad.php";
    //private String urla1 = "http://nationfis.hol.es/nonfc/regasign.php";
    private DatosDatos datosDatos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_asignatura, container, false);
        Spinner semestre =  view.findViewById(R.id.semestre);
        nombre = view.findViewById(R.id.nombre);
        Button enviar =  view.findViewById(R.id.enviar);
        enviar.setOnClickListener(RegistrarAsignaturaFragment.this);
        String matricula1 = "regasig";
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        Integer ep = preferences.getInt("ep",0);
        new RecibirSemestres(getActivity(),urla,ep,semestre,semestre,matricula1,0,0).execute();
        datosDatos = new DatosDatos();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.enviar:
                enviar1();
                break;
        }
    }

    private void enviar1() {
        String nombre1 = nombre.getText().toString();
        Integer semestre = datosDatos.getSemestres();
        if (nombre1.length() <= 0 ){
            Toast.makeText(getActivity(),"Rellene los campos porfavor",Toast.LENGTH_SHORT).show();
        }else {
            new RegistrarAsignaturas(getActivity(), urla, nombre1, semestre).execute();
        }
    }
}
