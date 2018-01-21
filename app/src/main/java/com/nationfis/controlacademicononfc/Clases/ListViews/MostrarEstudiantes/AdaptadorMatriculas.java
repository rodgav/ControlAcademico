package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarEstudiantes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.ActualizarActivo.ActualizarActivo;
import com.nationfis.controlacademicononfc.R;

import java.util.ArrayList;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/*
 * Created by Sam on 30/08/2017.
 */

public class AdaptadorMatriculas extends BaseAdapter {
    private Context c;
    private ArrayList<Matriculas> matriculas1;
    private LayoutInflater inflater;
    private String activoid,accion;
    private String asis [] = {"DESHABILITAR","HABILITAR"};
    AdaptadorMatriculas(Context c, ArrayList<Matriculas> matriculas1, String accion) {
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.c = c;
        this.matriculas1 = matriculas1;
        this.accion = accion;
    }

    @Override
    public int getCount() {
        return matriculas1.size();
    }

    @Override
    public Object getItem(int i) {
        return matriculas1.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view = inflater.inflate(R.layout.custom_matriculas_activar,viewGroup,false);
        }
        TextView nombre = view.findViewById(R.id.nombre);
        TextView codigo = view.findViewById(R.id.codigo);
        TextView ep = view.findViewById(R.id.ep);
        final TextView semestre = view.findViewById(R.id.semestre);
        ImageView foto = view.findViewById(R.id.foto);
        final TextView activado = view.findViewById(R.id.activado);
        final ImageButton menu = view.findViewById(R.id.menu);

        Matriculas matriculas = matriculas1.get(i);
        nombre.setText(matriculas.getNombre());
        codigo.setText(matriculas.getUser());
        ep.setText(matriculas.getNombreescuela());
        semestre.setText(matriculas.getNombresemestre());

        String imagen = matriculas.getFoto();
        byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
        foto.setImageBitmap(bitmap);
        String asi = "activado";
        String falt = "inactivo";
        if (Objects.equals(matriculas.getActivo(),"1")){
            activado.setText(asi);
            activado.setTextColor(ContextCompat.getColor(c,R.color.activo));
        }else {
            activado.setText(falt);
            activado.setTextColor(ContextCompat.getColor(c,R.color.inactivo));
        }
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(c,view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_activar_estudiantes,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        final int idm = menuItem.getItemId();
                        if (idm == R.id.activar){
                            final Dialog d = new Dialog(c);
                            d.setContentView(R.layout.dialog_activar_matriculas);
                            TextView nombre1 =  d.findViewById(R.id.nombre);
                            TextView ep1 =  d.findViewById(R.id.ep);
                            TextView semestre1 =  d.findViewById(R.id.semestre);
                            final TextView codigo1 =  d.findViewById(R.id.codigo);
                            Spinner activo =  d.findViewById(R.id.activo);
                            ImageView foto1 =  d.findViewById(R.id.foto);
                            Button enviar =  d.findViewById(R.id.enviar);

                            nombre1.setText(matriculas1.get(i).getNombre());
                            codigo1.setText(matriculas1.get(i).getUser());
                            ep1.setText(matriculas1.get(i).getNombreescuela());
                            semestre1.setText(matriculas1.get(i).getNombresemestre());
                            String imagen1 = matriculas1.get(i).getFoto();
                            byte[] bytes = Base64.decode(imagen1, Base64.DEFAULT);
                            Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            foto1.setImageBitmap(bitmap1);

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.simple_list_item_1, asis);
                            activo.setAdapter(adapter);
                            activo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i1, long l) {
                                    activoid = Integer.toString(i1);
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            if (Objects.equals(accion,"412566367c67448b599d1b7666f8ccfc")){//mn
                                enviar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String accion = MD5.encrypt("act");
                                        String accion1 = MD5.encrypt("m1");
                                        new ActualizarActivo(c,urla,accion,accion1,activoid,matriculas1.get(i).getUser(),d,activado).execute();
                                    }
                                });
                            }else if (Objects.equals(accion,"b74df323e3939b563635a2cba7a7afba")){//ma
                                enviar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String accion = MD5.encrypt("act");
                                        String accion1 = MD5.encrypt("m2");
                                        new ActualizarActivo(c,urla,accion,accion1,activoid,matriculas1.get(i).getUser(),d,activado).execute();
                                    }
                                });
                            }

                            d.show();
                            return true;
                        }else if (idm == R.id.modificar){
                            /*Bundle bundle = new Bundle();
                            Fragment fragment = new ActualizarPerfilFragment();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorn, fragment);
                            fragmentTransaction.addToBackStack(null).commit();*/
                            return true;
                        }
                        else {
                            return onMenuItemClick(menuItem);
                        }
                    }
                });
                popupMenu.show();
            }
        });
        return view;
    }
}