package com.nationfis.controlacademicononfc.Fragments;


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
import com.nationfis.controlacademicononfc.R;

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
        Spinner asignaturas =  view.findViewById(R.id.asignaturas);
        Spinner escuelas =  view.findViewById(R.id.escuelas);
        Spinner facultades =  view.findViewById(R.id.facultades);
        nombre = view.findViewById(R.id.nombre);
        Button enviar =  view.findViewById(R.id.enviar);
        enviar.setOnClickListener(RegistrarAsignaturaFragment.this);
        String accion= MD5.encrypt("facultades");
        String matricula1 = "regasig";
        new RecibirFacultades(getActivity(),urla, facultades, escuelas, semestre, asignaturas, matricula1,accion).execute();
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
        String semestre = datosDatos.getSemestres();
        if (nombre1.length() <= 0 ){
            Toast.makeText(getActivity(),"Rellene los campos porfavor",Toast.LENGTH_SHORT).show();
        }else {
            new RegistrarAsignaturas(getActivity(), urla, nombre1, semestre).execute();
        }
    }
}
