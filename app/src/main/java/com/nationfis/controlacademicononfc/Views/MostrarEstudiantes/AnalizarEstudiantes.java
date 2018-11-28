package com.nationfis.controlacademicononfc.Views.MostrarEstudiantes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.TAG;


/*
 * Created by Sam on 20/08/2017.
 */

public class AnalizarEstudiantes extends AsyncTask<Void,Void,Integer>{
    @SuppressLint("StaticFieldLeak")
    private Context c;
    private String s,accion;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView estudiantes;
    @SuppressLint("StaticFieldLeak")
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Estudiantes> estudiantes1 = new ArrayList<>();
    AnalizarEstudiantes(Context c, String s, RecyclerView estudiantes, String accion, SwipeRefreshLayout swipeRefreshLayout) {
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
        //Log.w(TAG,s);
        if (//estudiantes--docentes
                Objects.equals(accion,"0b97504fc4747fa8098d4899d1d08347") || Objects.equals(accion,"c6dfb15cab81b9a83ade09529ff082db")){
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
                    Integer documentoid = jo.getInt("documentoid");
                    Integer documento = jo.getInt("documento");
                    Integer codigo = jo.getInt("codigo");
                    Integer tipoid = jo.getInt("tipoid");
                    Integer generoid = jo.getInt("generoid");
                    Integer telefono = jo.getInt("telefono");
                    String nacimiento = jo.getString("nacimiento");
                    Integer lnacimientoid = jo.getInt("lnacimientoid");
                    Integer activo = jo.getInt("activo");
                    Integer epid = jo.getInt("epid");
                    String documenton = jo.getString("documenton");
                    String genero = jo.getString("genero");
                    String tipo = jo.getString("tipo");
                    String lnacimiento = jo.getString("lnacimiento");
                    String ep = jo.getString("ep");
                    Integer sede = jo.getInt("sede");
                    String nsede = jo.getString("nsede");

                    estudiantes2 = new Estudiantes();

                    estudiantes2.setNombre(nombre);
                    estudiantes2.setFoto(foto);
                    estudiantes2.setCorreo(correo);
                    estudiantes2.setDocumentoid(documentoid);
                    estudiantes2.setDocumento(documento);
                    estudiantes2.setCodigo(codigo);
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
        }else if (//matriculas
                Objects.equals(accion,"b74df323e3939b563635a2cba7a7afba") || Objects.equals(accion,"412566367c67448b599d1b7666f8ccfc")){
            try {
                Estudiantes estudiantes;
                JSONObject jo;
                JSONArray ja = new JSONArray(s);
                estudiantes1.clear();
                for (int i = 0; i<ja.length();i++){
                    jo=ja.getJSONObject(i);
                    String recibo = jo.getString("recibo");
                    Integer user = jo.getInt("user");
                    Integer semestre = jo.getInt("semestre");
                    String anioa = jo.getString("anioa");
                    Integer ep = jo.getInt("ep");
                    Integer tipom = jo.getInt("tipom");
                    Integer sede = jo.getInt("sede");
                    Integer activo = jo.getInt("activo");
                    String nombre = jo.getString("nombre");
                    String nombresemestre = jo.getString("nombresemestre");
                    String nombreescuela = jo.getString("nombreescuela");
                    String matriculanombre = jo.getString("matriculanombre");
                    String nombredistrito = jo.getString("nombredistrito");
                    String foto = jo.getString("foto");

                    estudiantes = new Estudiantes();

                    estudiantes.setRecibo(recibo);
                    estudiantes.setCodigo(user);
                    estudiantes.setSemestre(semestre);
                    estudiantes.setAnioa(anioa);
                    estudiantes.setEpid(ep);
                    estudiantes.setTipom(tipom);
                    estudiantes.setSede(sede);
                    estudiantes.setActivo(activo);
                    estudiantes.setNombre(nombre);
                    estudiantes.setNombresemestre(nombresemestre);
                    estudiantes.setNombreescuela(nombreescuela);
                    estudiantes.setMatriculanombre(matriculanombre);
                    estudiantes.setNombredistrito(nombredistrito);
                    estudiantes.setFoto(foto);

                    estudiantes1.add(estudiantes);
                }
                return 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (//asignaturasdoc
                Objects.equals(accion,"d36ef148e7d528f45a0c1409762820d7")){
            try {
                Estudiantes estudiantes;
                JSONObject jo;
                JSONArray ja = new JSONArray(s);
                estudiantes1.clear();
                for (int i = 0; i<ja.length();i++){
                    jo=ja.getJSONObject(i);
                    Integer codigo = jo.getInt("codigo");
                    String nombre = jo.getString("nombrec");
                    String foto = jo.getString("foto");
                    Integer codigoa = jo.getInt("codigoa");
                    String nombrea = jo.getString("nombrea");
                    Integer codigos = jo.getInt("codigos");
                    String nombree = jo.getString("nombree");

                    estudiantes = new Estudiantes();

                    estudiantes.setCodigo(codigo);
                    estudiantes.setNombre(nombre);
                    estudiantes.setFoto(foto);
                    estudiantes.setCodigoAsig(codigoa);
                    estudiantes.setNombreAsig(nombrea);
                    estudiantes.setSemestre(codigos);
                    estudiantes.setEp(nombree);

                    estudiantes1.add(estudiantes);
                }
                return 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (//horariodoc
                Objects.equals(accion,"6843023606e501b553a9be96e911df40")){
            Log.w(TAG,s);
            try {
                Estudiantes estudiantes;
                JSONObject jo;
                JSONArray ja = new JSONArray(s);
                estudiantes1.clear();
                for (int i = 0; i<ja.length();i++){
                    jo=ja.getJSONObject(i);

                    String inicio = jo.getString("inicio");
                    String fin = jo.getString("fin");
                    Integer codigoa = jo.getInt("codigoa");
                    String nombrea = jo.getString("nombrea");
                    Integer codigod = jo.getInt("codigod");
                    String nombred = jo.getString("nombred");
                    String nombrec = jo.getString("nombrec");
                    String foto = jo.getString("foto");
                    String nombree = jo.getString("nombree");
                    Integer codigo = jo.getInt("codigo");

                    estudiantes = new Estudiantes();

                    estudiantes.setInicio(inicio);
                    estudiantes.setFin(fin);
                    estudiantes.setCodigoAsig(codigoa);
                    estudiantes.setNombreAsig(nombrea);
                    estudiantes.setIdDia(codigod);
                    estudiantes.setNombreDia(nombred);
                    estudiantes.setNombre(nombrec);
                    estudiantes.setFoto(foto);
                    estudiantes.setEp(nombree);
                    estudiantes.setCodigo(codigo);

                    estudiantes1.add(estudiantes);
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
            AdaptadorEstudiantes a = new AdaptadorEstudiantes(c,estudiantes1,accion);

            estudiantes.setLayoutManager(new LinearLayoutManager(c));
            estudiantes.setAdapter(a);
            estudiantes.addItemDecoration(new DividerItemDecoration(c,DividerItemDecoration.VERTICAL));

            a.setItemTouchHelperExtension();
        }
    }
}
