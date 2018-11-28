package com.nationfis.controlacademicononfc.Views.MostrarAsignaturas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Views.ItemClickListener;
import com.nationfis.controlacademicononfc.R;

import java.util.ArrayList;
import java.util.Objects;

/*
 * Created by GlobalTIC's on 1/02/2018.
 */

public class AdaptadorAsignaturas extends RecyclerView.Adapter<CuerpoAsiganturas> {
    private Context c;
    private ArrayList<AsignaturasA>asignatura;
    AdaptadorAsignaturas(Context c, ArrayList<AsignaturasA> asignatura) {
        this.c = c;
        this.asignatura = asignatura;
    }

    @NonNull
    @Override
    public CuerpoAsiganturas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_asignaturas,parent,false);
        return new CuerpoAsiganturas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuerpoAsiganturas holder, int position) {
        AsignaturasA asignatura1 = asignatura.get(position);
        holder.nombre.setText(asignatura1.getNombre());
        holder.codigo.setText(String.valueOf(asignatura1.getCodigo()));
        String imagen = asignatura1.getFoto();
        holder.nombrea.setText(asignatura1.getNombrea());
        holder.modo.setText(asignatura1.getModo());
        byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
        holder.foto.setImageBitmap(bitmap);
        String asi = "activado";
        String falt = "inactivo";
        if (Objects.equals(asignatura1.getActivo(),1)){
            holder.activo.setText(asi);
            holder.activo.setTextColor(ContextCompat.getColor(c,R.color.activo));
        }else {
            holder.activo.setText(falt);
            holder.activo.setTextColor(ContextCompat.getColor(c,R.color.inactivo));
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(c,asignatura.get(pos).getModo(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return asignatura.size();
    }
}
