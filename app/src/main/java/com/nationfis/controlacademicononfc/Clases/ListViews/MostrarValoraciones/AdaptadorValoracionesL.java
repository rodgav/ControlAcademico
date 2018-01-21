package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarValoraciones;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.ActualizarValoraciones.ActualizarValoraciones;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.R;

import java.util.ArrayList;

/**
 * Created by Sam on 18/06/2017.
 */

public class AdaptadorValoracionesL extends BaseAdapter {
    private String urla = "http://nationfis.hol.es/academico/entrada.php";
    private ArrayList<ValoracionesL>valoracionesLs;
    private LayoutInflater inflater;
    private DatosDatos datosDatos;
    private Context c;
    private SharedPreferences preferences;
    AdaptadorValoracionesL(Context c, ArrayList<ValoracionesL> valoracionesLs) {
        this.c = c;
        this.valoracionesLs = valoracionesLs;
        this.datosDatos = new DatosDatos();
        this.inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.preferences = c.getSharedPreferences("datos", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return valoracionesLs.size();
    }

    @Override
    public Object getItem(int i) {
        return valoracionesLs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view = inflater.inflate(R.layout.custom_valoraciones,viewGroup,false);
        }
        TextView nombre = view.findViewById(R.id.nombre);
        final TextView peso = view.findViewById(R.id.peso);
        TextView nombrea = view.findViewById(R.id.asig);
        TextView nombreu = view.findViewById(R.id.unid);

        final ValoracionesL va = valoracionesLs.get(i);
        nombre.setText(va.getNombre());
        peso.setText(va.getPeso());
        nombrea.setText(va.getNombrea());
        nombreu.setText(va.getNombreu());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(c);
                d.setContentView(R.layout.dialog_valoraciones);
                TextView nombre1 =  d.findViewById(R.id.nombre1);
                final EditText peso1 = d.findViewById(R.id.peso1);
                TextView asig1 =  d.findViewById(R.id.asig1);
                TextView unid1 =  d.findViewById(R.id.unid1);
                Button registrar =  d.findViewById(R.id.registrar);

                nombre1.setText(valoracionesLs.get(i).getNombre());
                peso1.setText(valoracionesLs.get(i).getPeso());
                asig1.setText(valoracionesLs.get(i).getNombrea());
                unid1.setText(valoracionesLs.get(i).getNombreu());

                registrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String coduni =datosDatos.getUnidades();
                        String codasi = datosDatos.getAsignaturasd();
                        String accion = MD5.encrypt("actuaval");
                        String codval = valoracionesLs.get(i).getCodigo();
                        String codigo = preferences.getString("codigo","");
                        String peso2 = peso1.getText().toString();
                        if (peso2.length()<=0){
                            Toast.makeText(c,"Rellene los campos porfavor",Toast.LENGTH_SHORT).show();
                        }else {
                            int p;
                            p = Integer.parseInt(peso2);
                            if (p>=0 && p<=100){
                                new ActualizarValoraciones(c, urla, accion, codasi, coduni, codval, codigo,peso2,d,peso).execute();
                            }
                            else {
                                Toast.makeText(c,"Ingrese un numero del 0 al 100",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                d.show();
            }
        });
        return view;
    }
}
