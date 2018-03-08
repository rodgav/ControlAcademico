package com.nationfis.controlacademicononfc.Fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.DatosDatos;
import com.nationfis.controlacademicononfc.Clases.Register.EnviarRegistro;
import com.nationfis.controlacademicononfc.Clases.Spinners.Departamento.RecibirDepartamentos;
import com.nationfis.controlacademicononfc.Clases.Spinners.Facultades.RecibirFacultades;
import com.nationfis.controlacademicononfc.Clases.Spinners.Sede.RecibirSede;
import com.nationfis.controlacademicononfc.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private CheckBox femenino, masculino;
    private EditText fecha, nombre, apellidop, apellidom, correo, dni, codigo, contraseña, verificacion, telefono;
    private ImageView foto;
    private Integer tdocumento1;
    private String[] tdocumentos = {"DNI", "CARNET EXT.", "RUC", "PASAPORTE", "P. NAC", "OTROS"};
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private DatosDatos datosDatos = new DatosDatos();

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        masculino = view.findViewById(R.id.maculino);
        femenino = view.findViewById(R.id.femenino);
        fecha = view.findViewById(R.id.fecha);
        nombre = view.findViewById(R.id.nombre);
        apellidop = view.findViewById(R.id.apellidop);
        apellidom = view.findViewById(R.id.apellidom);
        correo = view.findViewById(R.id.correo);
        dni = view.findViewById(R.id.dni);
        codigo = view.findViewById(R.id.codigo);
        contraseña = view.findViewById(R.id.contraseña);
        verificacion = view.findViewById(R.id.verificacion);
        telefono = view.findViewById(R.id.telefono);
        foto = view.findViewById(R.id.foto);
        Button registrar = view.findViewById(R.id.register);
        Spinner spdocumento = view.findViewById(R.id.spdni);
        Spinner sedes = view.findViewById(R.id.sedes);
        Spinner facultades = view.findViewById(R.id.facultades);
        Spinner escuelas = view.findViewById(R.id.escuelas);
        Spinner departamento = view.findViewById(R.id.departamento);
        Spinner provincia = view.findViewById(R.id.provincia);
        Spinner distrito = view.findViewById(R.id.distrito);

        fecha.setText(sdf.format(calendar.getTime()));

        String accion = MD5.encrypt("facultades");
        String matricula1 = "regsemest";
        new RecibirFacultades(getActivity(), urla, facultades, escuelas, facultades, escuelas, matricula1, accion).execute();
        String accion1 = MD5.encrypt("departamentos");
        String accion2 = MD5.encrypt("provincias");
        String accion3 = MD5.encrypt("distritos");
        new RecibirDepartamentos(getActivity(), urla, departamento, provincia, distrito, accion1, accion2, accion3).execute();
        String accion0 = MD5.encrypt("sede");
        new RecibirSede(getActivity(), urla, accion0, sedes).execute();

        if (getActivity()!=null){
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, tdocumentos);
            spdocumento.setAdapter(adaptador);
            spdocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //String nombrei = (String) (adapterView).getSelectedItem();

                    tdocumento1 = i + 1;
                    //Toast.makeText(getActivity(), nombrei, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        registrar.setOnClickListener(RegisterFragment.this);
        fecha.setOnClickListener(RegisterFragment.this);
        masculino.setOnClickListener(RegisterFragment.this);
        femenino.setOnClickListener(RegisterFragment.this);
        foto.setOnClickListener(RegisterFragment.this);

        return view;
    }

    private void date() {
        if (getActivity()!=null)
        new DatePickerDialog(getActivity(), d, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updatedate();
        }
    };

    private void updatedate() {
        fecha.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fecha:
                date();
                break;
            case R.id.femenino:
                masculino.setChecked(false);
                break;
            case R.id.maculino:
                femenino.setChecked(false);
                break;
            case R.id.register:
                registrarnuevo();
                break;
            case R.id.foto:
                sacarfoto();
                break;
        }
    }

    private void sacarfoto() {
        Intent tomarfoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //noinspection ConstantConditions
        if (tomarfoto.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(tomarfoto, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            assert bundle != null;
            Bitmap imagen = (Bitmap) bundle.get("data");
            foto.setImageBitmap(imagen);
        }
    }

    private String convertirfoto(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    private void registrarnuevo() {
        String nombre1 = nombre.getText().toString().trim();
        String apellidop1 = apellidop.getText().toString().trim();
        String apellidom1 = apellidom.getText().toString().trim();
        String correo1 = correo.getText().toString().trim();
        foto.buildDrawingCache();
        Bitmap bitmap = foto.getDrawingCache();
        String foto1 = convertirfoto(bitmap);
        Integer sexo1;
        String contraseña1 = MD5.encrypt(contraseña.getText().toString().trim());
        String verificacion1 = verificacion.getText().toString().trim();
        String fecha1 = fecha.getText().toString();
        Integer activo = 0;
        Integer lnacimiento = datosDatos.getDistrito();
        Integer escuela = datosDatos.getEscuel();
        Integer sede = datosDatos.getSedes();
        //String urla ="http://nationfis.hol.es/nonfc/register.php";
        if (!masculino.isChecked() && !femenino.isChecked()) {
            Toast.makeText(getActivity(), "Por favor escoja su genero", Toast.LENGTH_SHORT).show();
        } else {
            if (masculino.isChecked()) {
                sexo1 = 1;
            } else {
                sexo1 = 2;
            }
            try {
                if (nombre1.length() <= 0 || apellidop1.length() <= 0 || apellidom1.length() <= 0 || correo1.length() <= 0
                        || dni.getText().toString().trim().length() <= 0 || codigo.getText().toString().trim().length() <= 0
                        || contraseña.getText().toString().trim().length() <= 0 || verificacion1.length() <= 0
                        || telefono.getText().toString().trim().length() <= 0 || fecha1.length() <= 0 || foto1.length() <= 0
                        || lnacimiento == null || escuela == null || sede == null) {
                    Toast.makeText(getActivity(), "Rellene todos los campos porfavor", Toast.LENGTH_SHORT).show();
                } else {
                    if (telefono.getText().toString().trim().length() == 9) {
                        if (contraseña.getText().toString().trim().length() >= 8) {
                            String contra = contraseña.getText().toString().trim();
                            if (CheckContra(contra)) {
                                Integer documento1 = Integer.valueOf(dni.getText().toString().trim());
                                Integer codigo1 = Integer.valueOf(codigo.getText().toString().trim());
                                Integer telefono1 = Integer.valueOf(telefono.getText().toString().trim());
                                new EnviarRegistro(getActivity(), urla, nombre1, apellidop1, apellidom1, correo1, documento1,
                                        sexo1, codigo1, contraseña1, verificacion1, telefono1, fecha1, foto1, tdocumento1, lnacimiento, activo, escuela, sede).execute();
                            }else {
                                Toast.makeText(getActivity(), "La contraseña solo puede contener mayusculas, minusculas y numeros", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "La contraseña debe tener como minimo 8 caractares", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "El numero de telefono debe contener 9 digitos", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean CheckContra(String contra) {
        boolean respuesta = false;
        if ((contra).matches("([0-9]|[a-z]|[A-Z])+")) {
            respuesta = true;
        }
        return respuesta;
    }
}
