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
import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes.RecibirAsignaturasDocentes;
import com.nationfis.controlacademicononfc.R;
import com.nationfis.controlacademicononfc.Views.MostrarMatriculados.EnviarAsistencia;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarMatriculadosAsignaturaFragment extends Fragment implements View.OnClickListener {


    public MostrarMatriculadosAsignaturaFragment() {
        // Required empty public constructor
    }
    private String anioa;
    private Integer sede;
    private DatosDatos datosDatos;
    private RecyclerView estudiantes,estudiantesa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_mostrar_matriculados_asignatura, container, false);

        estudiantes = view.findViewById(R.id.estudiantes);
        estudiantesa = view.findViewById(R.id.estudiantesa);
        Spinner asignaturas = view.findViewById(R.id.asignaturas);
        Button mostrar = view.findViewById(R.id.mostrar);
        //String urla="http://nationfis.hol.es/nonfc/asignaturad.php";

        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        Integer codigo = preferences.getInt("codigo", 0);
        sede = preferences.getInt("sede",0);
        datosDatos = new DatosDatos();

        anioa = getResources().getString(R.string.año);

        estudiantes.setLayoutManager(new LinearLayoutManager(getActivity()));
        estudiantes.setNestedScrollingEnabled(false);
        estudiantesa.setLayoutManager(new LinearLayoutManager(getActivity()));
        estudiantesa.setNestedScrollingEnabled(false);
        new RecibirAsignaturasDocentes(getActivity(), urla, codigo, asignaturas).execute();
        mostrar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mostrar:
                if (datosDatos.getAsignaturasd()!=null){
                    llenarestudiante1(datosDatos.getAsignaturasd());
                }
                break;

        }
    }

    private void llenarestudiante1(Integer asig) {
        //String urla = "http://nationfis.hol.es/nonfc/estudiantesn.php";
        //String urla1 = "http://nationfis.hol.es/nonfc/estudiantesa.php";
        String accion1 = MD5.encrypt("estudiantesn");
        String accion2 = MD5.encrypt("estudiantesa");
        EnviarAsistencia enviarAsistencia = new EnviarAsistencia(getActivity(),urla,asig,estudiantes,accion1,sede,anioa);
        enviarAsistencia.execute();

        EnviarAsistencia enviarAsistencia1 = new EnviarAsistencia(getActivity(),urla,datosDatos.getAsignaturasd(),estudiantesa,accion2,sede,anioa);
        enviarAsistencia1.execute();
    }
}
