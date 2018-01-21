package com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarNotas;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.ActualizarNota.ActualizarNota;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.R;

import java.util.ArrayList;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/*
 * Created by Sam on 06/06/2017.
 */

public class AdaptadorComprobarNotas extends BaseAdapter {
    private LayoutInflater inflater;
    private String tipo;
    private Context c;
    private ArrayList<NotasCN>notaCNs;
    private DatosDatos da;
    private SharedPreferences preferences;
    AdaptadorComprobarNotas(Context c, ArrayList<NotasCN> notaCNs, String tipo) {
        this.c = c;
        this.tipo = tipo;
        this.notaCNs = notaCNs;
        this.da = new DatosDatos();
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.preferences = c.getSharedPreferences("datos", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return notaCNs.size();
    }

    @Override
    public Object getItem(int i) {
        return notaCNs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (Objects.equals(tipo,"doc")){
            if (view==null){
                view = inflater.inflate(R.layout.custom_notas_docentes,viewGroup,false);
            }
            ImageView foto = view.findViewById(R.id.foto);
            TextView nombre = view.findViewById(R.id.nombre);
            TextView codigo = view.findViewById(R.id.codigo);
            final TextView nota = view.findViewById(R.id.nota);
            NotasCN notas = notaCNs.get(i);
            nombre.setText(notas.getNombre());
            codigo.setText(notas.getCodigo());
            nota.setText(notas.getNota());
            String imagen = notas.getFoto();
            byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
            foto.setImageBitmap(bitmap);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog d = new Dialog(c);
                    d.setContentView(R.layout.dialog_notas);
                    ImageView foto1 =d.findViewById(R.id.foto);
                    TextView nombre1 = d.findViewById(R.id.nombre);
                    final TextView codigo1 = d.findViewById(R.id.codigo);
                    final EditText nota1 = d.findViewById(R.id.nota);
                    Button registrar1 = d.findViewById(R.id.registrar);

                    nombre1.setText(notaCNs.get(i).getNombre());
                    codigo1.setText(notaCNs.get(i).getCodigo());
                    nota1.setText(notaCNs.get(i).getNota());
                    String imagen = notaCNs.get(i).getFoto();
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
                            String codval = da.getValoraciones();
                            String nota2 = nota1.getText().toString();
                            String accion = MD5.encrypt("actnot");
                            if(codigo2.length()<=0 || nota2.length()<=0){
                                Toast.makeText(c,"Rellene los campos",Toast.LENGTH_SHORT).show();
                            }else {
                                int no;
                                no=Integer.parseInt(nota2);
                                if (no>=0 && no<=20){
                                    new ActualizarNota(c,urla,accion,nota2,codval,codasi,coduni,codigo2,d,nota,codigo3).execute();
                                }else {
                                    Toast.makeText(c,"Ingrese una nota entre el 0 y el 20",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    d.show();
                }
            });
            return view;
        }else if (Objects.equals(tipo,"est")){
            if (view==null){
                view = inflater.inflate(R.layout.custom_notas_estudiantes,viewGroup,false);
            }
            ImageView foto = view.findViewById(R.id.foto);
            TextView nombre = view.findViewById(R.id.nombre);
            TextView nombrepeso = view.findViewById(R.id.nombrepeso);
            TextView nota = view.findViewById(R.id.nota);
            TextView peso = view.findViewById(R.id.peso);
            NotasCN notas = notaCNs.get(i);
            nombre.setText(notas.getNombre());
            nombrepeso.setText(notas.getCodigo());
            nota.setText(notas.getNota());
            String simb="%";
            String peso1 = notas.getPeso()+" "+simb;
            peso.setText(peso1);
            String imagen = notas.getFoto();
            byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
            foto.setImageBitmap(bitmap);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int nota1;
                    int peso1;
                    double resultado;
                    nota1 = Integer.parseInt(notaCNs.get(i).getNota());
                    peso1 = Integer.parseInt(notaCNs.get(i).getPeso());
                    resultado = ((nota1 * peso1)/100);
                    String resul = String.valueOf(Math.round(resultado));
                    Toast.makeText(c,resul,Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
        return view;
    }
}
