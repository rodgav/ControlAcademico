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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.RegistrarValoraciones.RegistrarValoraciones;
import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes.RecibirAsignaturasDocentes;
import com.nationfis.controlacademicononfc.Clases.Spinners.Unidad.RecibirUnidades;
import com.nationfis.controlacademicononfc.R;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarValoracionesFragment extends Fragment implements View.OnClickListener{


    public RegistrarValoracionesFragment() {
        // Required empty public constructor
    }
    private EditText nombre,peso;
    private ListView estudiantes, estudiante;
    private String codigo;
    DatosDatos datosDatos = new DatosDatos();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_valoraciones, container, false);
        nombre = view.findViewById(R.id.nombre);
        peso = view.findViewById(R.id.peso);
        Spinner unidad =  view.findViewById(R.id.unidad);
        Spinner asignatura =  view.findViewById(R.id.asignatura);
        Button enviar =  view.findViewById(R.id.enviar);
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        String tipo = preferences.getString("a", "");

        codigo = preferences.getString("codigo","");
        String sede = preferences.getString("sede", "");
        String anioa = getResources().getString(R.string.año);
        //String urla="http://nationfis.hol.es/nonfc/asignaturad.php";
        String accion= MD5.encrypt("unidades");

        new RecibirAsignaturasDocentes(getActivity(),urla,codigo, asignatura,estudiantes,estudiante, tipo, sede, anioa).execute();
        new RecibirUnidades(getActivity(),urla, unidad,accion).execute();
        enviar.setOnClickListener(RegistrarValoracionesFragment.this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.enviar:
                registrar();
                break;
        }
    }

    private void registrar() {
        String nombre1 = nombre.getText().toString();
        String peso1 = peso.getText().toString();
        String unidad1 = datosDatos.getUnidades();
        String codigoasi1 = datosDatos.getAsignaturasd();
        String accion = MD5.encrypt("registrarvalo");
        if (peso1.length()<=0 || nombre.length() <= 0) {
            Toast.makeText(getActivity(), "Rellene los campos porfavor", Toast.LENGTH_SHORT).show();
        }else {
            int peso4;
            peso4 = Integer.parseInt(peso1);
            if (peso4>=0 && peso4<=100){
                new RegistrarValoraciones(getActivity(),urla,nombre1,peso1,unidad1,codigoasi1,accion,codigo).execute();
            }else {
                Toast.makeText(getActivity(),"Ingrese una valoracion del 0 al 100",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
