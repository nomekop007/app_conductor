package com.example.app_conductor.model;

public class coordenada {

    private  String idTrasporte ;
    private double longitud;
    private  double latitud;


    public coordenada() {

    }

    public String getIdTrasporte() {
        return idTrasporte;
    }

    public void setIdTrasporte(String idTrasporte) {
        this.idTrasporte = idTrasporte;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
