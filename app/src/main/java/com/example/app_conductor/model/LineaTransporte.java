package com.example.app_conductor.model;

public class LineaTransporte {

    private String idLineaTransporte;
    private String idAgencia;
    private String nombreLinea;

    public LineaTransporte() {
    }

    public String getIdLineaTransporte() {
        return idLineaTransporte;
    }

    public void setIdLineaTransporte(String idLineaTransporte) {
        this.idLineaTransporte = idLineaTransporte;
    }

    public String getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(String idAgencia) {
        this.idAgencia = idAgencia;
    }

    public String getNombreLinea() {
        return nombreLinea;
    }

    public void setNombreLinea(String nombreLinea) {
        this.nombreLinea = nombreLinea;
    }
}
