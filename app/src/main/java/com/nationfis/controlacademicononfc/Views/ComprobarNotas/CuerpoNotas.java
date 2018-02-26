package com.nationfis.controlacademicononfc.Views.ComprobarNotas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nationfis.controlacademicononfc.Views.ItemClickListener;
import com.nationfis.controlacademicononfc.R;

/*
 * Created by GlobalTIC's on 1/02/2018.
 */

public class CuerpoNotas extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;
    ImageView foto;
    TextView nombre,codigo,nota;
    CuerpoNotas(View itemView) {
        super(itemView);
        foto = itemView.findViewById(R.id.foto);
        nombre = itemView.findViewById(R.id.nombre);
        codigo = itemView.findViewById(R.id.codigo);
        nota = itemView.findViewById(R.id.nota);
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
