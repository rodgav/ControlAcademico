package com.nationfis.controlacademicononfc.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes.RecibirAsignaturasDocentes;
import com.nationfis.controlacademicononfc.R;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarMatriculadosAsignaturaFragment extends Fragment {


    public MostrarMatriculadosAsignaturaFragment() {
        // Required empty public constructor
    }
    private String codigo,sede,anioa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_mostrar_matriculados_asignatura, container, false);

        ListView estudiantes = (ListView)view.findViewById(R.id.estudiantes);
        ListView estudiantesa = (ListView)view.findViewById(R.id.estudiantesa);
        Spinner asignaturas = (Spinner)view.findViewById(R.id.asignaturas);
        //String urla="http://nationfis.hol.es/nonfc/asignaturad.php";
        String tipo = "manual";

        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        codigo = preferences.getString("codigo","");
        sede = preferences.getString("sede","");
        anioa =  getResources().getString(R.string.año);

        new RecibirAsignaturasDocentes(getActivity(),urla,codigo,asignaturas,estudiantes,estudiantesa,tipo,sede,anioa).execute();
        //Toast.makeText(getActivity(),sede+" "+anioa,Toast.LENGTH_SHORT).show();
        return view;
    }

}
