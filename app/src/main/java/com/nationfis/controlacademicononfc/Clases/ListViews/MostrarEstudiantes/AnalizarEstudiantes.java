package com.nationfis.controlacademicononfc.Clases.ListViews.MostrarEstudiantes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/*
 * Created by Sam on 20/08/2017.
 */

public class AnalizarEstudiantes extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s,accion;
    @SuppressLint("StaticFieldLeak")
    private ListView estudiantes;
    @SuppressLint("StaticFieldLeak")
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Estudiantes> estudiantes1 = new ArrayList<>();
    private ArrayList<Matriculas> matriculas1 = new ArrayList<>();
    AnalizarEstudiantes(Context c, String s, ListView estudiantes, String accion, SwipeRefreshLayout swipeRefreshLayout) {
        this.c = c;
        this.s = s;
        this.estudiantes = estudiantes;
        this.accion = accion;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        if (Objects.equals(accion,"0b97504fc4747fa8098d4899d1d08347")){
            try {
                Estudiantes estudiantes2;
                JSONObject jo;
                JSONArray ja = new JSONArray(s);
                estudiantes1.clear();
                for (int i = 0; i<ja.length();i++){
                    jo=ja.getJSONObject(i);
                    String nombre = jo.getString("nombre");
                    String foto = jo.getString("foto");
                    String correo = jo.getString("correo");
                    String documentoid = jo.getString("documentoid");
                    String documento = jo.getString("documento");
                    String codigo = jo.getString("codigo");
                    String password = jo.getString("password");
                    String tipoid = jo.getString("tipoid");
                    String generoid = jo.getString("generoid");
                    String telefono = jo.getString("telefono");
                    String nacimiento = jo.getString("nacimiento");
                    String lnacimientoid = jo.getString("lnacimientoid");
                    String activo = jo.getString("activo");
                    String epid = jo.getString("epid");
                    String documenton = jo.getString("documenton");
                    String genero = jo.getString("genero");
                    String tipo = jo.getString("tipo");
                    String lnacimiento = jo.getString("lnacimiento");
                    String ep = jo.getString("ep");
                    String sede = jo.getString("sede");
                    String nsede = jo.getString("nsede");

                    estudiantes2 = new Estudiantes();

                    estudiantes2.setNombre(nombre);
                    estudiantes2.setFoto(foto);
                    estudiantes2.setCorreo(correo);
                    estudiantes2.setDocumentoid(documentoid);
                    estudiantes2.setDocumento(documento);
                    estudiantes2.setCodigo(codigo);
                    estudiantes2.setPassword(password);
                    estudiantes2.setTipoid(tipoid);
                    estudiantes2.setGeneroid(generoid);
                    estudiantes2.setTelefono(telefono);
                    estudiantes2.setNacimiento(nacimiento);
                    estudiantes2.setLnacimientoid(lnacimientoid);
                    estudiantes2.setActivo(activo);
                    estudiantes2.setEpid(epid);
                    estudiantes2.setDocumenton(documenton);
                    estudiantes2.setGenero(genero);
                    estudiantes2.setTipo(tipo);
                    estudiantes2.setLnacimiento(lnacimiento);
                    estudiantes2.setEp(ep);
                    estudiantes2.setSede(sede);
                    estudiantes2.setNsede(nsede);
                    estudiantes1.add(estudiantes2);
                }
                return 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            try {
                Matriculas matriculas;
                JSONObject jo;
                JSONArray ja = new JSONArray(s);
                matriculas1.clear();
                for (int i = 0; i<ja.length();i++){
                    jo=ja.getJSONObject(i);
                    String recibo = jo.getString("recibo");
                    String user = jo.getString("user");
                    String semestre = jo.getString("semestre");
                    String anioa = jo.getString("anioa");
                    String ep = jo.getString("ep");
                    String tipom = jo.getString("tipom");
                    String sede = jo.getString("sede");
                    String activo = jo.getString("activo");
                    String nombre = jo.getString("nombre");
                    String nombresemestre = jo.getString("nombresemestre");
                    String nombreescuela = jo.getString("nombreescuela");
                    String matriculanombre = jo.getString("matriculanombre");
                    String nombredistrito = jo.getString("nombredistrito");
                    String foto = jo.getString("foto");

                    matriculas = new Matriculas();

                    matriculas.setRecibo(recibo);
                    matriculas.setUser(user);
                    matriculas.setSemestre(semestre);
                    matriculas.setAnioa(anioa);
                    matriculas.setEp(ep);
                    matriculas.setTipom(tipom);
                    matriculas.setSede(sede);
                    matriculas.setActivo(activo);
                    matriculas.setNombre(nombre);
                    matriculas.setNombresemestre(nombresemestre);
                    matriculas.setNombreescuela(nombreescuela);
                    matriculas.setMatriculanombre(matriculanombre);
                    matriculas.setNombredistrito(nombredistrito);
                    matriculas.setFoto(foto);

                    matriculas1.add(matriculas);
                }
                return 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        swipeRefreshLayout.setRefreshing(false);
        if (integer==0){
            Toast.makeText(c,"No se pudieron cargar",Toast.LENGTH_SHORT).show();
        }else {
            if (Objects.equals(accion,"0b97504fc4747fa8098d4899d1d08347")){
                AdaptadorEstudiantes a = new AdaptadorEstudiantes(c,estudiantes1);
                estudiantes.setAdapter(a);
            }else {
                AdaptadorMatriculas a = new AdaptadorMatriculas(c,matriculas1,accion);
                estudiantes.setAdapter(a);
            }
        }
    }
}
