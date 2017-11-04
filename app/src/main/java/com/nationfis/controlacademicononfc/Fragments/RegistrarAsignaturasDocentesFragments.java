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
import com.nationfis.controlacademicononfc.Clases.RegistrarAsignaturasDocentes.RegistrarAsignaturasDocentes;
import com.nationfis.controlacademicononfc.Clases.Spinners.Facultades.RecibirFacultades;
import com.nationfis.controlacademicononfc.R;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarAsignaturasDocentesFragments extends Fragment implements View.OnClickListener {


    public RegistrarAsignaturasDocentesFragments() {
        // Required empty public constructor
    }
    private String matricula1 = "matriculaa";
    //private String urla = "http://nationfis.hol.es/nonfc/facultad.php";
    //private String urla1 = "http://nationfis.hol.es/nonfc/regasignd.php";
    private EditText codigo;
    private DatosDatos da;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_asignaturas_docentes_fragments, container, false);
        Spinner facultades = (Spinner)view.findViewById(R.id.facultades);
        Spinner escuelas = (Spinner)view.findViewById(R.id.escuelas);
        Spinner semestres = (Spinner)view.findViewById(R.id.semestres);
        Spinner asignaturas = (Spinner)view.findViewById(R.id.asignaturas);
        codigo = (EditText)view.findViewById(R.id.codigo);
        Button registrar = (Button)view.findViewById(R.id.registrar);
        String accion = MD5.encrypt("facultades");
        new RecibirFacultades(getActivity(),urla,facultades,escuelas,semestres,asignaturas,matricula1,accion).execute();
        registrar.setOnClickListener(RegistrarAsignaturasDocentesFragments.this);
        da = new DatosDatos();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registrar:
                enviar();
                break;
        }
    }

    private void enviar() {
        String codio = da.getAsignaturas();
        String codigo1 = codigo.getText().toString();
        if (codigo1.length() <= 0 ){
            Toast.makeText(getActivity(),"Rellene los campos porfavor",Toast.LENGTH_SHORT).show();
        }else {
            new RegistrarAsignaturasDocentes(getActivity(), urla, codio, codigo1).execute();
        }
    }
}
