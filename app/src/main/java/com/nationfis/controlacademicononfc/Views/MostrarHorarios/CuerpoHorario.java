package com.nationfis.controlacademicononfc.Views.MostrarHorarios;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nationfis.controlacademicononfc.R;

/**
 * Created by GlobalTIC's on 17/02/2018.
 */

class CuerpoHorario extends RecyclerView.ViewHolder {
    TextView nombre;
    CuerpoHorario(View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.nombre);
    }
}
