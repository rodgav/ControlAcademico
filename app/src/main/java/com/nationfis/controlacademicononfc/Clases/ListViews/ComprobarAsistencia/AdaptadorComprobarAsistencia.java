package com.nationfis.controlacademicononfc.Clases.ListViews.ComprobarAsistencia;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nationfis.controlacademicononfc.Clases.ActualizarAsistencia.ActualizarAsistencia;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * Created by Sam on 25/04/2017.
 */

public class AdaptadorComprobarAsistencia extends BaseAdapter {
    private Context c;
    private ArrayList<AsistenciaCA>asistenciaCAs;
    private LayoutInflater inflater;
    private String asis [] = {"FALTA","PRESENTE"};
    //private String urla ="http://nationfis.hol.es/nonfc/actualizarasis.php";
    private String fecha,asistio,tipo;
    private DatosDatos datosDatos;
    SharedPreferences preferences;

    public AdaptadorComprobarAsistencia(Context c, ArrayList<AsistenciaCA> asistenciaCAs,String tipo) {
        this.asistenciaCAs = asistenciaCAs;
        this.c = c;
        this.datosDatos = new DatosDatos();
        this.tipo = tipo;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.preferences = c.getSharedPreferences("datos", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return asistenciaCAs.size();
    }

    @Override
    public Object getItem(int i) {
        return asistenciaCAs.get(i);
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
        TextView nombre = (TextView)view.findViewById(R.id.nombre);
        TextView codigo = (TextView)view.findViewById(R.id.codigo);
        ImageView foto = (ImageView)view.findViewById(R.id.foto);
        final TextView asistio1 = (TextView)view.findViewById(R.id.asistio);

        AsistenciaCA asistencia1 = asistenciaCAs.get(i);
        nombre.setText(asistencia1.getNombre());
        codigo.setText(asistencia1.getCodigo());
        String imagen = asistencia1.getFoto();
        byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
        foto.setImageBitmap(bitmap);
        String asi = "asistio";
        String falt = "falto";
        if (Objects.equals(asistencia1.getAsistio(),"1")){
            asistio1.setText(asi);
        }else {
            asistio1.setText(falt);
        }
        if (Objects.equals(tipo,"pasar")) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog d = new Dialog(c);
                    d.setContentView(R.layout.dialog_asistencia);
                    TextView nombre1 = (TextView) d.findViewById(R.id.nombre);
                    final TextView codigo1 = (TextView) d.findViewById(R.id.codigo);
                    Spinner asistencia2 = (Spinner) d.findViewById(R.id.asistencia);
                    ImageView foto1 = (ImageView) d.findViewById(R.id.foto);
                    Button enviar = (Button) d.findViewById(R.id.enviar);

                    nombre1.setText(asistenciaCAs.get(i).getNombre());
                    codigo1.setText(asistenciaCAs.get(i).getCodigo());
                    String imagen1 = asistenciaCAs.get(i).getFoto();
                    byte[] bytes = Base64.decode(imagen1, Base64.DEFAULT);
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    foto1.setImageBitmap(bitmap1);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, asis);
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
                            fecha = df.format(ca.getTime());

                            String codigodoc = preferences.getString("codigo","");
                            String codigoasig = datosDatos.getAsignaturasd();
                            String codigoest = codigo1.getText().toString();
                            //Toast.makeText(c,asistio+" "+codigodoc+" "+codigoasig+" "+codigoest+" "+fecha,Toast.LENGTH_SHORT).show();
                            new ActualizarAsistencia(c, urla, asistio, codigodoc, codigoasig, codigoest, fecha,d,asistio1).execute();
                        }
                    });
                    d.show();
                }
            });
        }else if (Objects.equals(tipo,"ver")){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(c,asistio1.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
