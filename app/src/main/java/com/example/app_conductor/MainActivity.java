package com.example.app_conductor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.app_conductor.model.LineaTrasporte;
import com.example.app_conductor.model.Trasporte;
import com.example.app_conductor.model.coordenada;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    Button btnGPS, btnGPS2;
    EditText txtUbicacion;

    //Extraer id del usuario logeado
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String id = mAuth.getUid();

    private Trasporte trasporte = new Trasporte();


    private DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseFirestore myColeccion = FirebaseFirestore.getInstance();


    //escuchadores de gps
    private LocationManager locationManager;
    private LocationListener locationListener;

    //declarar elementos de drawer
    private DrawerLayout drawerLayout;
    private android.support.v7.widget.Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private View header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGPS = findViewById(R.id.boton);
        btnGPS2 = findViewById(R.id.boton2);


        txtUbicacion = findViewById(R.id.gps);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation);
        header = ((NavigationView) findViewById(R.id.navigation)).getHeaderView(0);
        btnGPS2.setVisibility(View.INVISIBLE);
        perfilTrasporte();

        confDrawer();
        permisosDeGPS();


    }

    public void perfilTrasporte() {

        myColeccion.collection("Transporte")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot Transport = task.getResult();

                            trasporte.setIdTrasporte(Transport.getId() + "");
                            trasporte.setNombreConductor(Transport.get("nombreConductor") + "");
                            trasporte.setLineaTrasporte(Transport.get("lineaTrasporte") + "");
                            trasporte.setPatente(Transport.get("Patente") + "");

                            String estado = Transport.get("Estado") + "";
                            if (estado.equals("true")) {
                                ((TextView) header.findViewById(R.id.text_estado)).setText("Conductor Abilitado");
                                trasporte.setEstado(true);
                            } else {
                                ((TextView) header.findViewById(R.id.text_estado)).setText("Conductor Desabilitado");
                                trasporte.setEstado(false);
                            }


                            //mostrar los datos del perfil en el header del drawer
                            ((TextView) header.findViewById(R.id.text_nombre)).setText(trasporte.getNombreConductor());
                            ((TextView) header.findViewById(R.id.text_patente)).setText(trasporte.getPatente());
                            BuscarLineaTrasporte();



                        } else {
                            Log.e("error : ", task.getException() + "");
                        }
                    }
                });
    }


    public void BuscarLineaTrasporte() {
        myColeccion.collection("LineaTrasporte")
                .document(trasporte.getLineaTrasporte())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   DocumentSnapshot lineaTrasporte = task.getResult();
                                                 trasporte.setLineaTrasporte(lineaTrasporte.get("nombreLinea")+"");
                                                   ((TextView) header.findViewById(R.id.text_linea)).setText(trasporte.getLineaTrasporte());
                                               } else {
                                                   Log.e("error : ", task.getException() + "");
                                               }
                                           }
                                       }
                );
    }


    public void confDrawer() {

        //configuracion del comportamiento del drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.cerrar:
                        Toast.makeText(MainActivity.this, "sesion cerrada!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, login.class);

                        //apaga el lister de gps
                        try {
                            locationManager.removeUpdates(locationListener);
                        } catch (Exception e) {
                            Log.e("error : ", "listener desactivado");
                        }


                        //deja los valores en 0
                        coordenada c = new coordenada();
                        c.setIdTrasporte(id);
                        c.setLatitud(0);
                        c.setLongitud(0);
                        myDatabase.child("Coordenada").child(id).setValue(c);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        toolbar.setTitle("Ubicacion");
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

    }

    public void permisosDeGPS() {

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


    public void ActivarGPS(View view) {

        locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {

            //cuando cambia la posicion del gps los actualiza

            public void onLocationChanged(Location location) {
                coordenada c = new coordenada();
                //se extraen los datos
                c.setIdTrasporte(id);

                c.setLatitud(location.getLatitude());
                c.setLongitud(location.getLongitude());
                txtUbicacion.setText("" + location.getLatitude() + "," + location.getLongitude());

                if (!trasporte.getEstado()) {
                    c.setLatitud(0);
                    c.setLongitud(0);
                    txtUbicacion.setText("ubicacion Bloqueada");
                }


                //se actualiza la coordenada
                myDatabase.child("Coordenada").child(id).setValue(c);

                btnGPS2.setVisibility(View.VISIBLE);
                btnGPS.setVisibility(View.INVISIBLE);
            }


            //cuando cambie el estatus
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            //cuando el proovedor este abilitado
            public void onProviderEnabled(String provider) {
            }

            //cuado el proovedor este desabilitado
            public void onProviderDisabled(String provider) {
            }

        };

        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 20, 0, locationListener);


    }

    public void ApagarGPS(View view) {


        //apaga el lister de gps
        try {
            locationManager.removeUpdates(locationListener);
        } catch (Exception e) {
            Log.e("error : ", "listener desactivado");
        }
        //deja los valores en 0
        coordenada c = new coordenada();
        c.setIdTrasporte(id);
        c.setLatitud(0);
        c.setLongitud(0);
        myDatabase.child("Coordenada").child(id).setValue(c);
        btnGPS2.setVisibility(View.INVISIBLE);
        btnGPS.setVisibility(View.VISIBLE);
        txtUbicacion.setText("");
    }


    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawers();
    }
}
