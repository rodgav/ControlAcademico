package com.nationfis.controlacademicononfc.Views.ComprobarNotas;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.ActualizarNota.ActualizarNota;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Views.ItemClickListener;
import com.nationfis.controlacademicononfc.R;

import java.util.ArrayList;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/*
 * Created by GlobalTIC's on 1/02/2018.
 */

public class AdaptadorNotas extends RecyclerView.Adapter<CuerpoNotas> {
    private Context c;
    private ArrayList<NotasCN> notaCNs;
    private String tipo;
    private SharedPreferences preferences;
    private DatosDatos da;
    AdaptadorNotas(Context c, ArrayList<NotasCN> notaCNs, String tipo) {
        this.c = c;
        this.tipo = tipo;
        this.notaCNs = notaCNs;
        this.da = new DatosDatos();
        this.preferences = c.getSharedPreferences("datos", Context.MODE_PRIVATE);
    }

    @Override
    public CuerpoNotas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (Objects.equals(tipo,"doc")){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notas_docentes,parent,false);
        }else if (Objects.equals(tipo,"est")){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notas_estudiantes,parent,false);
        }
        return new CuerpoNotas(view);
    }

    @Override
    public void onBindViewHolder(final CuerpoNotas holder, final int position) {
        if (Objects.equals(tipo,"doc")){
            NotasCN notas = notaCNs.get(position);
            holder.nombre.setText(notas.getNombre());
            holder.codigo.setText(notas.getCodigo());
            holder.nota.setText(notas.getNota());
            String imagen = notas.getFoto();
            byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
            holder.foto.setImageBitmap(bitmap);

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    final Dialog d = new Dialog(c);
                    d.setContentView(R.layout.dialog_notas);
                    ImageView foto1 =d.findViewById(R.id.foto);
                    TextView nombre1 = d.findViewById(R.id.nombre);
                    final TextView codigo1 = d.findViewById(R.id.codigo);
                    final EditText nota1 = d.findViewById(R.id.nota);
                    Button registrar1 = d.findViewById(R.id.registrar);

                    nombre1.setText(notaCNs.get(pos).getNombre());
                    codigo1.setText(notaCNs.get(pos).getCodigo());
                    nota1.setText(notaCNs.get(pos).getNota());
                    String imagen = notaCNs.get(pos).getFoto();
                    byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
                    foto1.setImageBitmap(bitmap);
                    registrar1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String codigo3 = preferences.getString("codigo","");
                            String codigo2 = codigo1.getText().toString();
                            String coduni = da.getUnidades();
                            String codasi = da.getAsignaturasd();
                            String nota2 = nota1.getText().toString();
                            String accion = MD5.encrypt("actnot");
                            if(codigo2.length()<=0 || nota2.length()<=0){
                                Toast.makeText(c,"Rellene los campos",Toast.LENGTH_SHORT).show();
                            }else {
                                int no;
                                no=Integer.parseInt(nota2);
                                if (no>=0 && no<=20){
                                    new ActualizarNota(c,urla,accion,nota2,codasi,coduni,codigo2,d,holder.nota,codigo3).execute();
                                }else {
                                    Toast.makeText(c,"Ingrese una nota entre el 0 y el 20",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    Window window = d.getWindow();
                    assert window != null;
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.CENTER);
                    d.show();
                }
            });
        }else if (Objects.equals(tipo,"est")){
            NotasCN notas = notaCNs.get(position);
            holder.nombre.setText(notas.getNombre());
            holder.nombrepeso.setText(notas.getCodigo());
            holder.nota.setText(notas.getNota());
            String simb="%";
            String peso1 = notas.getPeso()+" "+simb;
            holder.peso.setText(peso1);
            String imagen = notas.getFoto();
            byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
            holder.foto.setImageBitmap(bitmap);

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    int nota1;
                    int peso1;
                    double resultado;
                    nota1 = Integer.parseInt(notaCNs.get(pos).getNota());
                    peso1 = Integer.parseInt(notaCNs.get(pos).getPeso());
                    resultado = ((nota1 * peso1)/100);
                    String resul = String.valueOf(Math.round(resultado));
                    Toast.makeText(c,resul,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notaCNs.size();
    }
}
