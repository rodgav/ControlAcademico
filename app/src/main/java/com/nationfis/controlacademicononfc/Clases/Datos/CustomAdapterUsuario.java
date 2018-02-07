package com.nationfis.controlacademicononfc.Clases.Datos;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.kosalgeek.android.md5simply.MD5;
import com.nationfis.controlacademicononfc.Views.ItemClickListener;
import com.nationfis.controlacademicononfc.Clases.Login.ComprobarLogin;
import com.nationfis.controlacademicononfc.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.urla;


/*
 * Created by Sam on 19/04/2017.
 */

public class CustomAdapterUsuario extends RecyclerView.Adapter<CuerpoUsuarios> {
    private ArrayList<UsuariosUsuarios> usuarioslist;
    private Context c;
    private LayoutInflater inflater;
    private UsuariosSqlite usuariosSqlite;
    public CustomAdapterUsuario(Context c, ArrayList<UsuariosUsuarios> usuarioslist) {
        this.c = c;
        this.usuarioslist = usuarioslist;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public CuerpoUsuarios onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarios,parent,false);
        CuerpoUsuarios cuerpoUsuarios = new CuerpoUsuarios(view);
        return cuerpoUsuarios;
    }

    @Override
    public void onBindViewHolder(CuerpoUsuarios holder, final int position) {

        UsuariosUsuarios usuariosUsuarios = usuarioslist.get(position);
        holder.tipo.setText(usuariosUsuarios.getTipo());
        holder.nombre.setText(usuariosUsuarios.getNombre());
        String image=(usuariosUsuarios.getImagen());
        byte[] byteImage = Base64.decode(image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
        holder.imagen.setImageBitmap(bitmap);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Dialog d = new Dialog(c);
                d.setContentView(R.layout.dialoglogin);
                TextView nombre = d.findViewById(R.id.nombre);
                TextView tipo = d.findViewById(R.id.tipo);
                ImageView imagen = d.findViewById(R.id.imagen);
                Button ingresar = d.findViewById(R.id.ingresar);
                final EditText password = d.findViewById(R.id.password);
                nombre.setText(usuarioslist.get(pos).getNombre());
                tipo.setText(usuarioslist.get(pos).getTipo());
                String imagen1 = usuarioslist.get(pos).getImagen();
                byte[] byteImage = Base64.decode(imagen1, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
                imagen.setImageBitmap(bitmap);
                final String codigo = usuarioslist.get(pos).getCodigo();
                /*Toast.makeText(c,TOKEN,Toast.LENGTH_SHORT).show();
                Log.w(TAG,TOKEN);*/
                ingresar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String password1 = MD5.encrypt(password.getText().toString().trim());
                        String TOKEN = FirebaseInstanceId.getInstance().getToken();
                        //String urla = "http://nationfis.hol.es/nonfc/login.php";
                        if (password.getText().toString().trim().length()<=0){
                            Toast.makeText(c,"Ingrese sus contraseña",Toast.LENGTH_SHORT).show();
                        }else {
                            if (TOKEN != null){
                                if (TOKEN.contains("{")){
                                    try{
                                        JSONObject jo = new JSONObject(TOKEN);
                                        String nuevotoken = jo.getString("token");
                                        new ComprobarLogin(c,urla,codigo,password1,nuevotoken).execute();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    new ComprobarLogin(c,urla,codigo,password1,TOKEN).execute();
                                }
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

        usuariosSqlite = new UsuariosSqlite(c,"UsersDB.sqlite",null,1);
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(c,view);
                popupMenu.getMenuInflater().inflate(R.menu.menuloginforonetouch,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int i = menuItem.getItemId();
                        if (i == R.id.eliminar){
                            try{
                                usuariosSqlite.deleteData(usuarioslist.get(position).getCodigo());
                                Toast.makeText(c,"Usuario Eliminado "+usuarioslist.get(position).getCodigo(),Toast.LENGTH_SHORT).show();
                                usuariosSqlite.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            usuarioslist.remove(position);
                            notifyDataSetChanged();
                            return true;
                        }else{
                            return onMenuItemClick(menuItem);
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarioslist.size();
    }


}
