package com.nationfis.controlacademicononfc.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarAsistencia.ComprobarAsistencia;
import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes.RecibirAsignaturasDocentes;
import com.nationfis.controlacademicononfc.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarAsistenciaFragment extends Fragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{


    public MostrarAsistenciaFragment() {
        // Required empty public constructor
    }
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    private ListView asistencia,estudiantesa;
    private String fecha1,codigo;
    private String tipo="ver";
    private TextView fecha;
    private Spinner asignaturas;
    private String nivel,sede,anioa;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private Button enviar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        nivel = preferences.getString("a","");
        codigo = preferences.getString("codigo","");
        sede = preferences.getString("sede","");
        anioa =  getResources().getString(R.string.año);

        if(Objects.equals(nivel, "e4e4564027d73a4325024d948d167e93")){
            view = inflater.inflate(R.layout.fragment_mostrar_asistencia_estudiante, container, false);
            asistencia = (ListView)view.findViewById(R.id.asistencia);
            fecha = (TextView)view.findViewById(R.id.fecha);
            enviar = (Button)view.findViewById(R.id.enviar);
            fecha.setText(sdf.format(calendar.getTime()));
            swipeRefreshLayout =(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);


        }else if (Objects.equals(nivel,"ac99fecf6fcb8c25d18788d14a5384ee")){
            view = inflater.inflate(R.layout.fragment_mostrar_asistencia, container, false);
            asistencia = (ListView)view.findViewById(R.id.asistencia);
            fecha = (TextView)view.findViewById(R.id.fecha);
            asignaturas = (Spinner)view.findViewById(R.id.asignaturas);
            enviar = (Button)view.findViewById(R.id.enviar);
            fecha.setText(sdf.format(calendar.getTime()));
            swipeRefreshLayout =(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
                new RecibirAsignaturasDocentes(getActivity(),urla,codigo,asignaturas,asistencia,estudiantesa,tipo,sede,anioa).execute();
        }
        fecha.setOnClickListener(MostrarAsistenciaFragment.this);
        enviar.setOnClickListener(MostrarAsistenciaFragment.this);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.enviar:
                asistencia.setAdapter(null);
                if(Objects.equals(nivel, "e4e4564027d73a4325024d948d167e93")){
                    enviare();
                }else if (Objects.equals(nivel,"ac99fecf6fcb8c25d18788d14a5384ee")){
                    enviard();
                }
                break;
            case R.id.fecha:
                date();
                break;
        }
    }

    private void enviard() {
        DatosDatos da = new DatosDatos();
        String fecha1 = fecha.getText().toString();
        String codigoa = da.getAsignaturasd();
        String accion= MD5.encrypt("mostrarasis");
        //String urla1="http://nationfis.hol.es/nonfc/asistidos1.php";
        //Toast.makeText(getActivity(),da.getAsignaturasd(),Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(true);
        new ComprobarAsistencia(getActivity(),urla,codigoa,fecha1,asistencia,tipo,accion,swipeRefreshLayout).execute();

    }

    private void enviare() {
        fecha1 = fecha.getText().toString();
        String accion= MD5.encrypt("mostrarasis1");
        //String urla = "http://nationfis.hol.es/nonfc/verasistencia.php";
        swipeRefreshLayout.setRefreshing(true);
        new ComprobarAsistencia(getActivity(), urla,codigo,fecha1,asistencia,tipo,accion,swipeRefreshLayout).execute();
    }
    private void date() {
        new DatePickerDialog(getActivity(),d,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updatedate();
        }
    };

    private void updatedate() {
        fecha.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onRefresh() {
        asistencia.setAdapter(null);
        if(Objects.equals(nivel, "e4e4564027d73a4325024d948d167e93")){
            enviare();
        }else if (Objects.equals(nivel,"ac99fecf6fcb8c25d18788d14a5384ee")){
            enviard();
        }
    }
}
