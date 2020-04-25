package com.example.app_conductor.model;

public class LineaTrasporte {

    private String idLineaTrasporte;
    private String idAgencia;
    private String nombreLinea;

    public LineaTrasporte() {

    }

    public String getIdLineaTrasporte() {
        return idLineaTrasporte;
    }

    public void setIdLineaTrasporte(String idLineaTrasporte) {
        this.idLineaTrasporte = idLineaTrasporte;
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
