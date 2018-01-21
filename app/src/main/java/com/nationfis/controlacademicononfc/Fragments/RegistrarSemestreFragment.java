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
import com.nationfis.controlacademicononfc.Clases.RegistrarSemestres.RegistrarSemestres;
import com.nationfis.controlacademicononfc.Clases.Spinners.Facultades.RecibirFacultades;
import com.nationfis.controlacademicononfc.R;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarSemestreFragment extends Fragment implements View.OnClickListener{

    public RegistrarSemestreFragment() {
        // Required empty public constructor
    }

    //private String urla = "http://nationfis.hol.es/nonfc/facultad.php";
    //private String urla1 = "http://nationfis.hol.es/nonfc/regsemes.php";
    private DatosDatos datosDatos;
    private EditText semestre;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_semestre, container, false);
        semestre = view.findViewById(R.id.semestre);
        Spinner facultades = view.findViewById(R.id.facultades);
        Spinner escuelas = view.findViewById(R.id.escuelas);
        Spinner semestres = view.findViewById(R.id.semestres);
        Spinner asignaturas = view.findViewById(R.id.asignaturas);
        Button enviar = view.findViewById(R.id.enviar);
        datosDatos = new DatosDatos();
        String accion= MD5.encrypt("facultades");
        String matricula1 = "regsemest";
        new RecibirFacultades(getActivity(),urla,facultades,escuelas,semestres,asignaturas, matricula1,accion).execute();
        enviar.setOnClickListener(RegistrarSemestreFragment.this);
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
        String ep = datosDatos.getEscuel();
        String sem = semestre.getText().toString();
        if(sem.length()<=0){
            Toast.makeText(getActivity(),"Rellene todos los campos porfavor",Toast.LENGTH_SHORT).show();
        }else {
            new RegistrarSemestres(getActivity(),urla,ep,sem).execute();
        }
    }
}
