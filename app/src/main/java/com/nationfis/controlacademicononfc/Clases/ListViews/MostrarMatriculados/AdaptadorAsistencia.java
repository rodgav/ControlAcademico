package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarMatriculados;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.R;

import java.util.ArrayList;
import java.util.Objects;

/*
 * Created by SamGM on 23/04/2017.
 */

public class AdaptadorAsistencia extends BaseAdapter {
    private LayoutInflater inflater;
    private Context c;
    private ArrayList<Asistencia>asistencia;
    AdaptadorAsistencia(Context c, ArrayList<Asistencia> asistencia) {
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.c = c;
        this.asistencia = asistencia;
    }

    @Override
    public int getCount() {
        return asistencia.size();
    }

    @Override
    public Object getItem(int i) {
        return asistencia.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view = inflater.inflate(R.layout.custom_estudiantes_pasar_asistencia,viewGroup,false);
        }
        TextView nombre = view.findViewById(R.id.nombre);
        TextView codigo = view.findViewById(R.id.codigo);
        ImageView foto = view.findViewById(R.id.foto);
        TextView activo = view.findViewById(R.id.activo);

        Asistencia asistencia1 = asistencia.get(i);
        nombre.setText(asistencia1.getNombre());
        codigo.setText(asistencia1.getCodigo());
        String imagen = asistencia1.getFoto();
        byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
        foto.setImageBitmap(bitmap);

        String asi = "activado";
        String falt = "inactivo";
        if (Objects.equals(asistencia1.getActivo(),"1")){
            activo.setText(asi);
            activo.setTextColor(ContextCompat.getColor(c,R.color.activo));
        }else {
            activo.setText(falt);
            activo.setTextColor(ContextCompat.getColor(c,R.color.inactivo));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c,asistencia.get(i).getNombre()+" "+asistencia.get(i).getCodigo(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
