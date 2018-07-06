package com.nationfis.controlacademicononfc.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.Reportes.Asistenciapdf.DescargarAsistenciaPDF;
import com.nationfis.controlacademicononfc.Views.ComprobarAsistencia.ComprobarAsistencia;
import com.nationfis.controlacademicononfc.Clases.Spinners.AsignaturasDocentes.RecibirAsignaturasDocentes;
import com.nationfis.controlacademicononfc.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarAsistenciaFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    public MostrarAsistenciaFragment() {
        // Required empty public constructor
    }

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView asistencia;
    private Integer codigo;
    private String tipo = "ver";
    private TextView fecha;
    private String nivel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private Button enviar;
    private DatosDatos da;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("datos", Context.MODE_PRIVATE);
        nivel = preferences.getString("a", "");
        codigo = preferences.getInt("codigo", 0);

        if (Objects.equals(nivel, "e4e4564027d73a4325024d948d167e93")) {
            view = inflater.inflate(R.layout.fragment_mostrar_asistencia_estudiante, container, false);
            asistencia = view.findViewById(R.id.asistencia);
            fecha = view.findViewById(R.id.fecha);
            enviar = view.findViewById(R.id.enviar);
            fecha.setText(sdf.format(calendar.getTime()));
            swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
            asistencia.setLayoutManager(new LinearLayoutManager(getActivity()));

        } else if (Objects.equals(nivel, "ac99fecf6fcb8c25d18788d14a5384ee")) {
            view = inflater.inflate(R.layout.fragment_mostrar_asistencia, container, false);

            asistencia = view.findViewById(R.id.asistencia);
            fecha = view.findViewById(R.id.fecha);
            Spinner asignaturas = view.findViewById(R.id.asignaturas);
            enviar = view.findViewById(R.id.enviar);
            Button descargar = view.findViewById(R.id.descargar);

            da = new DatosDatos();

            fecha.setText(sdf.format(calendar.getTime()));
            swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
            asistencia.setLayoutManager(new LinearLayoutManager(getActivity()));
            new RecibirAsignaturasDocentes(getActivity(), urla, codigo, asignaturas).execute();

            descargar.setOnClickListener(MostrarAsistenciaFragment.this);
        }

        fecha.setOnClickListener(MostrarAsistenciaFragment.this);
        enviar.setOnClickListener(MostrarAsistenciaFragment.this);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enviar:
                asistencia.setAdapter(null);
                if (Objects.equals(nivel, "e4e4564027d73a4325024d948d167e93")) {
                    enviare();
                } else if (Objects.equals(nivel, "ac99fecf6fcb8c25d18788d14a5384ee")) {
                    enviard();
                }
                break;
            case R.id.fecha:
                date();
                break;
            case R.id.descargar:
                descargar();
                break;
        }
    }

    private void descargar() {
        Integer asig = da.getAsignaturasd();
        Integer ep = preferences.getInt("ep",0);
        String accion = MD5.encrypt("asistenciapdf");
        new DescargarAsistenciaPDF(getActivity(), urla, accion, asig, fecha.getText().toString(),ep).execute();
    }
    private void enviard() {
        String fecha1 = fecha.getText().toString();
        Integer codigoa = da.getAsignaturasd();
        String accion = MD5.encrypt("mostrarasis");
        Integer a = 1;
        swipeRefreshLayout.setRefreshing(true);
        new ComprobarAsistencia(getActivity(), urla, codigoa, fecha1, asistencia, tipo, accion, swipeRefreshLayout,a).execute();

    }

    private void enviare() {
        String fecha1 = fecha.getText().toString();
        String accion = MD5.encrypt("mostrarasis1");
        Integer a = 2;
        swipeRefreshLayout.setRefreshing(true);
        new ComprobarAsistencia(getActivity(), urla, codigo, fecha1, asistencia, tipo, accion, swipeRefreshLayout,a).execute();
    }

    private void date() {
        new DatePickerDialog(getActivity(), d, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updatedate();
        }
    };

    private void updatedate() {
        fecha.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onRefresh() {
        asistencia.setAdapter(null);
        if (Objects.equals(nivel, "e4e4564027d73a4325024d948d167e93")) {
            enviare();
        } else if (Objects.equals(nivel, "ac99fecf6fcb8c25d18788d14a5384ee")) {
            enviard();
        }
    }
}
