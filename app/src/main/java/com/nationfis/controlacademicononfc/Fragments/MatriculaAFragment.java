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
import android.widget.TextView;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.RegistrarMatricula.RecibirMatricula;
import com.nationfis.controlacademicononfc.Clases.Spinners.Semestres.RecibirSemestres;
import com.nationfis.controlacademicononfc.Clases.Spinners.TipoMatricula.RecibirTipoMatricula;
import com.nationfis.controlacademicononfc.R;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatriculaAFragment extends Fragment implements View.OnClickListener {


    public MatriculaAFragment() {
        // Required empty public constructor
    }
    private EditText baucher;
    private TextView año;
    private String matricula1 = "matriculaa";
    DatosDatos datosDatos;
    private String usuario;
    private String ep;
    private String sede;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matricula_a, container, false);
        baucher = view.findViewById(R.id.baucher);
        EditText codigo =  view.findViewById(R.id.codigo);
        año = view.findViewById(R.id.año);
        EditText sed =  view.findViewById(R.id.sede);
        Button matricula =  view.findViewById(R.id.matricula);
        Spinner asignatura =  view.findViewById(R.id.asignatura);
        Spinner semestre =  view.findViewById(R.id.semestre);
        Spinner tipom =  view.findViewById(R.id.tipom);
        //String urla = "http://nationfis.hol.es/nonfc/facultad.php";
        //String urla1 = "http://nationfis.hol.es/nonfc/tipomatricula.php";
        datosDatos = new DatosDatos();
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        usuario = preferences.getString("codigo","");
        ep = preferences.getString("ep","");
        sede = preferences.getString("sede","");
        String seden = preferences.getString("seden", "");
        codigo.setText(usuario);
        sed.setText(seden);
        String accion1= MD5.encrypt("matricula");
        new RecibirSemestres(getActivity(),urla,ep,semestre,asignatura,matricula1).execute();
        new RecibirTipoMatricula(getActivity(),urla,tipom,accion1).execute();
        matricula.setOnClickListener(MatriculaAFragment.this);
        return view;
    }

    @Override
    public void onClick(View view) {
        String baucher1 = baucher.getText().toString();
        String anio = año.getText().toString();
        String asignatura = datosDatos.getAsignaturas();
        String tipom = datosDatos.getTiposm();
        String activo = "0";
        //String urla ="http://nationfis.hol.es/nonfc/matricula.php";
        switch (view.getId()){
            case R.id.matricula:
                new RecibirMatricula(getActivity(),urla,baucher1,usuario,matricula1,anio,ep,asignatura,tipom,activo,sede).execute();
                break;
        }
    }
}
