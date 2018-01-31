package com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarAsistencia;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nationfis.controlacademicononfc.Clases.ListViews.ItemClickListener;
import com.nationfis.controlacademicononfc.R;

/**
 * Created by GlobalTIC's on 22/01/2018.
 */

public class CuerpoAsistencia extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ItemClickListener itemClickListener;
    ImageView foto;
    TextView nombre,codigo,asistio,activo;
    public CuerpoAsistencia(View itemView) {
        super(itemView);
        foto = itemView.findViewById(R.id.foto);
        nombre = itemView.findViewById(R.id.nombre);
        codigo = itemView.findViewById(R.id.codigo);
        asistio = itemView.findViewById(R.id.asistio);
        activo = itemView.findViewById(R.id.activo);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
