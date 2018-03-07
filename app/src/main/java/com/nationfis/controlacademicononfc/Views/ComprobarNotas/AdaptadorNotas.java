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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notas_docentes, parent, false);
        return new CuerpoNotas(view);
    }

    @Override
    public void onBindViewHolder(final CuerpoNotas holder, final int position) {
        final NotasCN notas = notaCNs.get(position);
        holder.nombre.setText(notas.getNombre());
        holder.codigo.setText(String.valueOf(notas.getCodigo()));
        holder.nota.setText(String.valueOf(notas.getNota()));
        String imagen = notas.getFoto();
        byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        holder.foto.setImageBitmap(bitmap);
        if (Objects.equals(tipo, "doc")) {
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    final Dialog d = new Dialog(c);
                    d.setContentView(R.layout.dialog_notas);
                    ImageView foto1 = d.findViewById(R.id.foto);
                    TextView nombre1 = d.findViewById(R.id.nombre);
                    final TextView codigo1 = d.findViewById(R.id.codigo);
                    final EditText nota1 = d.findViewById(R.id.nota);
                    Button registrar1 = d.findViewById(R.id.registrar);

                    nombre1.setText(notaCNs.get(pos).getNombre());
                    codigo1.setText(String.valueOf(notaCNs.get(pos).getCodigo()));
                    nota1.setText(String.valueOf(notaCNs.get(pos).getNota()));
                    String imagen = notaCNs.get(pos).getFoto();
                    byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                    foto1.setImageBitmap(bitmap);
                    registrar1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Integer codigo3 = preferences.getInt("codigo", 0);
                            Integer codigo2 = Integer.valueOf(codigo1.getText().toString());
                            Integer coduni = da.getUnidades();
                            Integer codasi = da.getAsignaturasd();
                            Integer nota2 = Integer.valueOf(nota1.getText().toString());
                            String accion = MD5.encrypt("actnot");
                            String anioa = c.getResources().getString(R.string.año);
                            if (codigo1.getText().toString().trim().length() <= 0 || nota1.getText().toString().trim().length() <= 0) {
                                Toast.makeText(c, "Rellene los campos", Toast.LENGTH_SHORT).show();
                            } else {
                                if (nota2 >= 0 && nota2 <= 20) {
                                    new ActualizarNota(c, urla, accion, nota2, codasi, coduni, codigo2, d, holder.nota, codigo3, anioa).execute();
                                } else {
                                    Toast.makeText(c, "Ingrese una nota entre el 0 y el 20", Toast.LENGTH_SHORT).show();
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
        } else {
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    Toast.makeText(c, notas.getNota(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notaCNs.size();
    }
}
