package com.nationfis.controlacademicononfc.Clases.Datos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nationfis.controlacademicononfc.Views.ItemClickListener;
import com.nationfis.controlacademicononfc.R;

/**
 * Created by GlobalTIC's on 1/02/2018.
 */

public class CuerpoUsuarios extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;
    TextView tipo,nombre;
    ImageView imagen;
    ImageButton menu;
    CuerpoUsuarios(View itemView) {
        super(itemView);
        tipo = itemView.findViewById(R.id.tipo);
        nombre = itemView.findViewById(R.id.nombre);
        imagen = itemView.findViewById(R.id.imagen);
        menu = itemView.findViewById(R.id.menu);
        itemView.setOnClickListener(this);
    }
    void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(getLayoutPosition());
    }
}
