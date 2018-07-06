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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopeer.itemtouchhelperextension.Extension;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;
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
    private ItemTouchHelperExtension mItemTouchHelperExtension;

    void setItemTouchHelperExtension(ItemTouchHelperExtension itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }

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
        return new ItemSwipeWithActionWidthViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final CuerpoAsistencia holder, @SuppressLint("RecyclerView") final int position) {
        holder.bind(asistenciaCAS.get(position));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, holder.asistio.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        if (Objects.equals(tipo, "pasar")) {

            holder.act.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN :
                            //holder.act.setImageResource(c.getResources().getColor(R.color.inactivo));
                            holder.act.setBackgroundColor(c.getResources().getColor(R.color.inactivo));
                            break;
                        case MotionEvent.ACTION_UP :
                            holder.act.setBackgroundColor(c.getResources().getColor(R.color.azul));

                            break;
                    }
                    return true;
                }
            });
            ItemSwipeWithActionWidthViewHolder viewHolder = (ItemSwipeWithActionWidthViewHolder) holder;

            viewHolder.mActionViewRefresh.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Objects.equals(holder.asistio.getText().toString(), "asistio")) {
                                Toast.makeText(c, "La asistencia es asistio", Toast.LENGTH_SHORT).show();
                            } else {
                                Integer codigodoc = preferences.getInt("codigo", 0);
                                Integer codigoasig = datosDatos.getAsignaturasd();
                                Integer codigoest = asistenciaCAS.get(position).getCodigo();
                                new ActualizarAsistencia(c, urla, 1, codigodoc, codigoasig, codigoest, holder.asistio).execute();
                            }
                        }
                    }
            );
            viewHolder.mActionViewDelete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Objects.equals(holder.asistio.getText().toString(), "falto")) {
                                Toast.makeText(c, "La asistencia es falto", Toast.LENGTH_SHORT).show();
                            } else {
                                Integer codigodoc = preferences.getInt("codigo", 0);
                                Integer codigoasig = datosDatos.getAsignaturasd();
                                Integer codigoest = asistenciaCAS.get(position).getCodigo();
                                new ActualizarAsistencia(c, urla, 0, codigodoc, codigoasig, codigoest, holder.asistio).execute();
                            }
                        }
                    }

            );
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
            } else {
                asistio.setText(falt);
            }
            /*itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mItemTouchHelperExtension.startDrag(CuerpoAsistencia.this);
                    }
                    return true;
                }
            });*/
        }
    }

    class ItemSwipeWithActionWidthViewHolder extends CuerpoAsistencia implements Extension {

        View mActionViewDelete;
        View mActionViewRefresh;

        ItemSwipeWithActionWidthViewHolder(View itemView) {
            super(itemView);
            mActionViewDelete = itemView.findViewById(R.id.delete);
            mActionViewRefresh = itemView.findViewById(R.id.update);
        }

        @Override
        public float getActionWidth() {
            return accion.getWidth();
        }
    }

}
