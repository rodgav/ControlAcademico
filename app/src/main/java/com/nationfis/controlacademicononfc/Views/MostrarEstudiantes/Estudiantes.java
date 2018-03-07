package com.nationfis.controlacademicononfc.Views.MostrarEstudiantes;

/*
 * Created by Sam on 20/08/2017.
 */

public class Estudiantes {
    private String correo,password,genero,nacimiento,lnacimiento,documenton,nsede,inicio,fin,nombreDia;
    private String recibo,anioa,nombre,nombresemestre,nombreescuela,matriculanombre,
            nombredistrito,foto,nombreAsig,ep,tipo;
    private Integer codigoAsig,codigoSede,idDia,codigo,documento,telefono,activo,documentoid,tipoid,generoid,
            lnacimientoid,epid,semestre,tipom,sede ;

    void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getFoto() {
        return foto;
    }

    public String getCorreo() {
        return correo;
    }

    public Integer getDocumento() {
        return documento;
    }

    void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGenero() {
        return genero;
    }

    void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getTelefono() {
        return telefono;
    }

    void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getLnacimiento() {
        return lnacimiento;
    }

    public void setLnacimiento(String lnacimiento) {
        this.lnacimiento = lnacimiento;
    }
    
    public Integer getDocumentoid() {
        return documentoid;
    }

    void setDocumentoid(Integer documentoid) {
        this.documentoid = documentoid;
    }

    public Integer getTipoid() {
        return tipoid;
    }

    void setTipoid(Integer tipoid) {
        this.tipoid = tipoid;
    }

    public Integer getGeneroid() {
        return generoid;
    }

    void setGeneroid(Integer generoid) {
        this.generoid = generoid;
    }

    public Integer getLnacimientoid() {
        return lnacimientoid;
    }

    void setLnacimientoid(Integer lnacimientoid) {
        this.lnacimientoid = lnacimientoid;
    }

    public Integer getEpid() {
        return epid;
    }

    void setEpid(Integer epid) {
        this.epid = epid;
    }

    public String getDocumenton() {
        return documenton;
    }

    void setDocumenton(String documenton) {
        this.documenton = documenton;
    }
    
    public String getNsede() {
        return nsede;
    }

    void setNsede(String nsede) {
        this.nsede = nsede;
    }

    public String getRecibo() {
        return recibo;
    }

    void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getAnioa() {
        return anioa;
    }

    public void setAnioa(String anioa) {
        this.anioa = anioa;
    }

    public String getEp() {
        return ep;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public Integer getTipom() {
        return tipom;
    }

    public void setTipom(Integer tipom) {
        this.tipom = tipom;
    }

    public Integer getSede() {
        return sede;
    }

    public void setSede(Integer sede) {
        this.sede = sede;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    String getNombresemestre() {
        return nombresemestre;
    }

    String getNombreescuela() {
        return nombreescuela;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    void setNombresemestre(String nombresemestre) {
        this.nombresemestre = nombresemestre;
    }

    void setNombreescuela(String nombreescuela) {
        this.nombreescuela = nombreescuela;
    }

    void setMatriculanombre(String matriculanombre) {
        this.matriculanombre = matriculanombre;
    }

    void setNombredistrito(String nombredistrito) {
        this.nombredistrito = nombredistrito;
    }

    void setCodigoAsig(Integer codigoAsig) {
        this.codigoAsig = codigoAsig;
    }

    void setNombreAsig(String nombreAsig) {
        this.nombreAsig = nombreAsig;
    }

    void setCodigoSede(Integer codigoSede) {
        this.codigoSede = codigoSede;
    }

    public String getMatriculanombre() {
        return matriculanombre;
    }

    public String getNombredistrito() {
        return nombredistrito;
    }

    public Integer getCodigoAsig() {
        return codigoAsig;
    }

    public Integer getCodigoSede() {
        return codigoSede;
    }

    public String getNombreAsig() {
        return nombreAsig;
    }


    void setInicio(String inicio) {
        this.inicio = inicio;
    }

    void setFin(String fin) {
        this.fin = fin;
    }

    void setIdDia(Integer idDia) {
        this.idDia = idDia;
    }

    void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
        return fin;
    }

    public Integer getIdDia() {
        return idDia;
    }

    public String getNombreDia() {
        return nombreDia;
    }
}
