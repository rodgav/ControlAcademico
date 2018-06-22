package com.nationfis.controlacademicononfc.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.nationfis.controlacademicononfc.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment implements View.OnClickListener{


    public PerfilFragment() {
        // Required empty public constructor
    }

    private TextView correo;
    private TextView telefono;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_perfil, container, false);

        ImageView foto = view.findViewById(R.id.circleImageView2);
        TextView codigo = view.findViewById(R.id.codigo);
        TextView apellidop = view.findViewById(R.id.apellidop);
        TextView apellidom = view.findViewById(R.id.apellidom);
        TextView nombres = view.findViewById(R.id.nombres);
        TextView tdoc = view.findViewById(R.id.tdoc);
        TextView ndoc = view.findViewById(R.id.ndoc);
        TextView genero = view.findViewById(R.id.genero);
        TextView lnacimiento = view.findViewById(R.id.lnacimiento);
        TextView fnacimiento = view.findViewById(R.id.fnacimiento);
        TextView tipo = view.findViewById(R.id.tipo);
        TextView activo = view.findViewById(R.id.activo);
        TextView ep = view.findViewById(R.id.ep);
        TextView sede = view.findViewById(R.id.sede);
        TextView password = view.findViewById(R.id.password);
        CardView cardView3 = view.findViewById(R.id.cardView3);
        telefono = view.findViewById(R.id.telefono);
        correo = view.findViewById(R.id.correo);

        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("datos", Context.MODE_PRIVATE);
        codigo.setText(String.valueOf(preferences.getInt("codigo",0)));
        apellidop.setText(preferences.getString("apellidop",""));
        apellidom.setText(preferences.getString("apellidom",""));
        nombres.setText(preferences.getString("nombres",""));
        tdoc.setText(preferences.getString("tdoc",""));
        ndoc.setText(String.valueOf(preferences.getInt("ndoc",0)));
        genero.setText(preferences.getString("genero",""));
        lnacimiento.setText(preferences.getString("lnacimiento",""));
        fnacimiento.setText(preferences.getString("fnacimiento",""));
        tipo.setText(preferences.getString("tipos",""));
        Integer activo1 = preferences.getInt("activo",0);
        String act = "activado";
        String des = "inactivo";
        if (Objects.equals(activo1,1)){
            activo.setText(act);
            activo.setTextColor(ContextCompat.getColor(getActivity(),R.color.blanco));
            activo.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.activo));
        }else {
            activo.setText(des);
            activo.setTextColor(ContextCompat.getColor(getActivity(),R.color.blanco));
            activo.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.inactivo));
        }
        ep.setText(preferences.getString("epn",""));
        sede.setText(preferences.getString("seden",""));
        password.setText("**********");
        telefono.setText(String.valueOf(preferences.getInt("telefono",0)));
        correo.setText(preferences.getString("correo",""));


        String image = preferences.getString("image", "");
        byte[] byteImage = Base64.decode(image, Base64.DEFAULT);
        Bitmap foto1 = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        foto.setImageBitmap(foto1);

        cardView3.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardView3:
                Menu(view);
                break;
        }
    }

    private void Menu(View view) {
        @SuppressLint("RtlHardcoded") PopupMenu popupMenu = new PopupMenu(getActivity(),view, Gravity.RIGHT);
        popupMenu.getMenuInflater().inflate(R.menu.menu_contacto,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.llamar:
                        Llamar();
                        break;
                    case R.id.mensaje:
                        EnviarMensaje(Integer.valueOf(telefono.getText().toString()));
                        break;
                    case R.id.correo:
                        EnviarCorreo();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void EnviarMensaje(Integer n) {
        Uri sms_uri = Uri.parse("smsto:+" + n);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", "");
        startActivity(sms_intent);
    }

    private void Llamar() {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + Integer.valueOf(telefono.getText().toString())));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        getActivity().startActivity(i);
    }

    private void EnviarCorreo() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{correo.getText().toString()});
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{correo.getText().toString()});
        //emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{correo.getText().toString()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "APP CONTROL ACADEMICO FIS");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        emailIntent.setType("message/rfc822");
        //Damos la opción al usuario que elija desde que app enviamos el email.
        startActivity(Intent.createChooser(emailIntent, "Selecciona una aplicación para enviar el correo"));
    }

}
