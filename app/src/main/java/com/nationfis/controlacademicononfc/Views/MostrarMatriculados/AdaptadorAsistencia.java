package com.nationfis.controlacademicononfc.Views.MostrarMatriculados;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class AdaptadorAsistencia extends RecyclerView.Adapter<CuerpoMatriculados>{
    private Context c;
    private ArrayList<Asistencia>asistencia;
    AdaptadorAsistencia(Context c, ArrayList<Asistencia> asistencia) {
        this.c = c;
        this.asistencia = asistencia;
    }

    @Override
    public CuerpoMatriculados onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_estudiantes_pasar_asistencia,parent,false);
        CuerpoMatriculados cuerpoAsistencia = new CuerpoMatriculados(view);
        return cuerpoAsistencia;    }

    @Override
    public void onBindViewHolder(CuerpoMatriculados holder, int position) {
        Asistencia asistencia1 = asistencia.get(position);
        holder.nombre.setText(asistencia1.getNombre());
        holder.codigo.setText(asistencia1.getCodigo());
        String imagen = asistencia1.getFoto();
        byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
        holder.foto.setImageBitmap(bitmap);

        String asi = "activado";
        String falt = "inactivo";
        if (Objects.equals(asistencia1.getActivo(),"1")){
            holder.activo.setText(asi);
            holder.activo.setTextColor(ContextCompat.getColor(c,R.color.activo));
        }else {
            holder.activo.setText(falt);
            holder.activo.setTextColor(ContextCompat.getColor(c,R.color.inactivo));
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(c,asistencia.get(pos).getNombre()+" "+asistencia.get(pos).getCodigo(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return asistencia.size();
    }
}
