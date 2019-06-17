package com.example.app_conductor.model;

public class Trasporte {

    private int calificacion;
    private String contraseña;
    private int edadConductor;
    private boolean estado;
    private int idLinea;
    private String idTrasporte;
    private String nombreConductor;
    private String patente;
    private String usuario;
    private String fotoConductorUrl;

    public Trasporte() {

    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getEdadConductor() {
        return edadConductor;
    }

    public void setEdadConductor(int edadConductor) {
        this.edadConductor = edadConductor;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public String getIdTrasporte() {
        return idTrasporte;
    }

    public void setIdTrasporte(String idTrasporte) {
        this.idTrasporte = idTrasporte;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFotoConductorUrl() {
        return fotoConductorUrl;
    }

    public void setFotoConductorUrl(String fotoConductorUrl) {
        this.fotoConductorUrl = fotoConductorUrl;
    }

}
