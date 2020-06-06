package com.example.app_conductor.model;

public class Transporte {




    private String idTransporte;
    private String nombreConductor;
    private String patente;
    private String lineaTransporte;
    private Boolean estado;



    public Transporte() {

    }

    public String getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(String idTransporte) {
        this.idTransporte = idTransporte;
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

    public String getLineaTransporte() {
        return lineaTransporte;
    }

    public void setLineaTransporte(String lineaTransporte) {
        this.lineaTransporte = lineaTransporte;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
