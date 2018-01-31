package com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarAsistencia;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.ActualizarAsistencia.ActualizarAsistencia;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.ListViews.ItemClickListener;
import com.nationfis.controlacademicononfc.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * Created by GlobalTIC's on 22/01/2018.
 */

public class AdaptadorAsistencia extends RecyclerView.Adapter<CuerpoAsistencia> {
    private Context c;
    private ArrayList<AsistenciaCA>asistenciaCAS;
    private String tipo,asistio;
    private SharedPreferences preferences;
    private DatosDatos datosDatos;
    private String asis [] = {"FALTA","PRESENTE"};
    public AdaptadorAsistencia(Context c, ArrayList<AsistenciaCA> asistenciaCAs,String tipo) {
        this.c = c;
        this.asistenciaCAS = asistenciaCAs;
        this.tipo = tipo;
        preferences = c.getSharedPreferences("datos",Context.MODE_PRIVATE);
        datosDatos = new DatosDatos();

    }

    @Override
    public CuerpoAsistencia onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_estudiantes_pasar_asistencia,parent,false);
        CuerpoAsistencia cuerpoAsistencia = new CuerpoAsistencia(view);
        return cuerpoAsistencia;
    }

    @Override
    public void onBindViewHolder(final CuerpoAsistencia holder, final int position) {
    AsistenciaCA asistenciaCA = asistenciaCAS.get(position);
        holder.nombre.setText(asistenciaCA.getNombre());
        holder.codigo.setText(asistenciaCA.getCodigo());
        String imagen = asistenciaCA.getFoto();
        byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
        holder.foto.setImageBitmap(bitmap);
        String asi = "asistio";
        String falt = "falto";
        if (Objects.equals(asistenciaCA.getAsistio(),"1")){
            holder.asistio.setText(asi);
        }else {
            holder.asistio.setText(falt);
        }
        if (Objects.equals(tipo,"pasar")) {
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    final Dialog d = new Dialog(c);
                    d.setContentView(R.layout.dialog_asistencia);
                    TextView nombre1 =  d.findViewById(R.id.nombre);
                    final TextView codigo1 =  d.findViewById(R.id.codigo);
                    Spinner asistencia2 =  d.findViewById(R.id.asistencia);
                    ImageView foto1 =  d.findViewById(R.id.foto);
                    Button enviar =  d.findViewById(R.id.enviar);

                    nombre1.setText(asistenciaCAS.get(position).getNombre());
                    codigo1.setText(asistenciaCAS.get(position).getCodigo());
                    String imagen1 = asistenciaCAS.get(position).getFoto();
                    byte[] bytes = Base64.decode(imagen1, Base64.DEFAULT);
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    foto1.setImageBitmap(bitmap1);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.simple_list_item_1, asis);
                    asistencia2.setAdapter(adapter);
                    asistencia2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i1, long l) {
                            asistio = Integer.toString(i1);
                            //Toast.makeText(c, asistio, Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                    enviar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Calendar ca = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String fecha = df.format(ca.getTime());

                            String codigodoc = preferences.getString("codigo","");
                            String codigoasig = datosDatos.getAsignaturasd();
                            String codigoest = codigo1.getText().toString();
                            //Toast.makeText(c,asistio+" "+codigodoc+" "+codigoasig+" "+codigoest+" "+fecha,Toast.LENGTH_SHORT).show();
                            new ActualizarAsistencia(c, urla, asistio, codigodoc, codigoasig, codigoest, fecha,d,holder.asistio).execute();
                        }
                    });
                    Window window = d.getWindow();
                    assert window != null;
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.CENTER);
                    d.show();
                }
            });

        }else if (Objects.equals(tipo,"ver")){
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    Toast.makeText(c,holder.asistio.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return asistenciaCAS.size();
    }
}
