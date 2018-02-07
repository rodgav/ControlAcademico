package com.nationfis.controlacademicononfc.Views.MostrarMatriculados;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nationfis.controlacademicononfc.Views.ItemClickListener;
import com.nationfis.controlacademicononfc.R;

/**
 * Created by GlobalTIC's on 1/02/2018.
 */

public class CuerpoMatriculados extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;
    TextView nombre,codigo,activo;
    ImageView foto;
    public CuerpoMatriculados(View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.nombre);
        codigo = itemView.findViewById(R.id.codigo);
        foto = itemView.findViewById(R.id.foto);
        activo = itemView.findViewById(R.id.activo);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(getLayoutPosition());
    }

    void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
