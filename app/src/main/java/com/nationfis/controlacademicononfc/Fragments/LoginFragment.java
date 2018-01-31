package com.nationfis.controlacademicononfc.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Clases.Login.ComprobarLogin;
import com.nationfis.controlacademicononfc.R;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    private EditText pass;
    private EditText user;

    public LoginFragment() {
        // Required empty public constructor
    }
    TextView refresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        pass = view.findViewById(R.id.pass);
        user = view.findViewById(R.id.user);
        Button reg = view.findViewById(R.id.button2);
        Button log = view.findViewById(R.id.button);

        reg.setOnClickListener(LoginFragment.this);
        log.setOnClickListener(LoginFragment.this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button2:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new RegisterFragment();
                fragmentTransaction.replace(R.id.contenedor,fragment);
                fragmentTransaction.addToBackStack(null).commit();
                break;
            case R.id.button:
                String usuario = user.getText().toString();
                String password = MD5.encrypt(pass.getText().toString());
                if(usuario.length()<=0 || password.length()<=0){
                    Toast.makeText(getActivity(),"Rellene los campos",Toast.LENGTH_SHORT).show();
                }else {
                    comprobarlogin(usuario,password);
                }
                break;
        }
    }
    private void comprobarlogin(String usuario, String password) {
        //String urla = "http://nationfis.hol.es/nonfc/login.php";
        new ComprobarLogin(getActivity(), urla,usuario,password).execute();
    }
}
