package com.nationfis.controlacademicononfc.Views.MostrarHorarios;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nationfis.controlacademicononfc.R;

import java.util.ArrayList;



/*
 * Created by GlobalTIC's on 15/02/2018.
 */

class AdaptadorHorario extends RecyclerView.Adapter<CuerpoHorario> {
    private Context c;
    private ArrayList<Horarios> horarios;
    AdaptadorHorario(Context c, ArrayList<Horarios> horarios) {
        this.c = c;
        this.horarios = horarios;
    }

    @Override
    public CuerpoHorario onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.custom_horario,parent,false);
        return new CuerpoHorario(view);
    }

    @Override
    public void onBindViewHolder(CuerpoHorario holder, int position) {
        Horarios horarios1 = horarios.get(position);
        holder.nombre.setText(horarios1.getNombre());
    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }

}
