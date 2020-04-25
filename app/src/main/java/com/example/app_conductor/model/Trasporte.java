package com.example.app_conductor.model;

public class Trasporte {




    private String idTrasporte;
    private String nombreConductor;
    private String Patente;
    private String lineaTrasporte;
    private Boolean Estado;



    public Trasporte() {

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
        return Patente;
    }

    public void setPatente(String patente) {
        Patente = patente;
    }

    public String getLineaTrasporte() {
        return lineaTrasporte;
    }

    public void setLineaTrasporte(String lineaTrasporte) {
        this.lineaTrasporte = lineaTrasporte;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }
}
