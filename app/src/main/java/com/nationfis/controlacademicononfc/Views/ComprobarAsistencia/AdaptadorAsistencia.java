package com.nationfis.controlacademicononfc.Views.ComprobarAsistencia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.ActualizarAsistencia.ActualizarAsistencia;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.R;

import java.util.ArrayList;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/*
 * Created by GlobalTIC's on 22/01/2018.
 */

public class AdaptadorAsistencia extends RecyclerView.Adapter<AdaptadorAsistencia.CuerpoAsistencia> {
    private Context c;
    private ArrayList<AsistenciaCA> asistenciaCAS;
    private String tipo;
    private Integer a;
    private SharedPreferences preferences;
    private DatosDatos datosDatos;


    AdaptadorAsistencia(Context c, ArrayList<AsistenciaCA> asistenciaCAs, String tipo, Integer a) {
        this.c = c;
        this.asistenciaCAS = asistenciaCAs;
        this.tipo = tipo;
        this.a = a;
        datosDatos = new DatosDatos();
        preferences = c.getSharedPreferences("datos", Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public CuerpoAsistencia onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_estudiantes_pasar_asistencia, parent, false);
        return new CuerpoAsistencia(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final CuerpoAsistencia holder, @SuppressLint("RecyclerView") final int position) {
        holder.bind(asistenciaCAS.get(position));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, holder.asistio.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        if (Objects.equals(tipo, "pasar")) {

            holder.act.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Objects.equals(holder.asistio.getText().toString(), "asistio")){
                        Integer codigodoc = preferences.getInt("codigo", 0);
                        Integer codigoasig = datosDatos.getAsignaturasd();
                        Integer codigoest = asistenciaCAS.get(position).getCodigo();
                        new ActualizarAsistencia(c, urla, 0, codigodoc, codigoasig, codigoest, holder.asistio,holder.act).execute();
                    }else if (Objects.equals(holder.asistio.getText().toString(), "falto")){
                        Integer codigodoc = preferences.getInt("codigo", 0);
                        Integer codigoasig = datosDatos.getAsignaturasd();
                        Integer codigoest = asistenciaCAS.get(position).getCodigo();
                        new ActualizarAsistencia(c, urla, 1, codigodoc, codigoasig, codigoest, holder.asistio,holder.act).execute();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return asistenciaCAS.size();
    }

    class CuerpoAsistencia extends RecyclerView.ViewHolder {
        ImageView foto,act;
        TextView nombre, codigo, asistio;
        View accion, view;

        CuerpoAsistencia(View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.foto);
            act = itemView.findViewById(R.id.act);
            nombre = itemView.findViewById(R.id.nombre);
            codigo = itemView.findViewById(R.id.codigo);
            asistio = itemView.findViewById(R.id.asistio);
            accion = itemView.findViewById(R.id.accion);
            view = itemView.findViewById(R.id.view_foreground);
        }

        void bind(AsistenciaCA asistenciaCA) {
            nombre.setText(asistenciaCA.getNombre());
            if (Objects.equals(a,1)){
                codigo.setText(String.valueOf(asistenciaCA.getCodigo()));
            }else {
                codigo.setText(asistenciaCA.getAsignatura());
            }
            String imagen = asistenciaCA.getFoto();
            byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
            foto.setImageBitmap(bitmap);
            String asi = "asistio";
            String falt = "falto";
            if (Objects.equals(asistenciaCA.getAsistio(), 1)) {
                asistio.setText(asi);
                act.setImageResource(R.mipmap.check);
            } else {
                asistio.setText(falt);
                act.setImageResource(R.mipmap.error);
            }

        }
    }
}
