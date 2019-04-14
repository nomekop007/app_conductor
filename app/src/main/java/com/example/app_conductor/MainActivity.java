package com.example.app_conductor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    Button btnGPS;
    TextView txtUbicacion;
     String id="";

    //hacer referencia a la base de datos de firebase
    DatabaseReference mydatabasereference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGPS = (Button) findViewById(R.id.boton);
        txtUbicacion = (TextView) findViewById(R.id.gps);


        crearCoordenada();
        permisosDeGPS();
    }






    public  void crearCoordenada(){
        // crear objeto coordeanada en firebase
        coordenada c = new coordenada();
        c.setIdTrasporte(UUID.randomUUID().toString());
        c.setLongitud(0);
        c.setLatitud(0);
        mydatabasereference.child("coordenada").child(c.getIdTrasporte()).setValue(c);

        //extrae la id
        id = c.getIdTrasporte();
    }



    public void permisosDeGPS(){

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        //pregunta si no tiene permiso
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            //de ser asi pregunta
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }
    }



    public void verGPS(View view) {

        LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            //cuando cambia la posicion del gps los actualiza
            public void onLocationChanged(Location location) {

                txtUbicacion.setText("" + location.getLatitude() + "," + location.getLongitude());

                coordenada c = new coordenada();
                //se extraen los datos
                c.setIdTrasporte(id);
                c.setLatitud(location.getLatitude());
                c.setLongitud(location.getLongitude());
            mydatabasereference.child("coordenada").child(id).setValue(c);

            }

            //cuando cambie el estatus
            public void onStatusChanged(String provider, int status, Bundle extras) { }
            //cuando el proovedor este abilitado
            public void onProviderEnabled(String provider) { }
            //cuado el proovedor este desabilitado
            public void onProviderDisabled(String provider) { }
        };

        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
}
