package com.example.app_conductor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_conductor.model.Trasporte;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity {


    EditText t_usuario, t_pass;

    DatabaseReference mydatabasereference = FirebaseDatabase.getInstance().getReference();

    private List<Trasporte> trasporteList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        t_usuario = findViewById(R.id.txt_usuario);
        t_pass = findViewById(R.id.txt_pass);

        crearLista();
    }





    public void iniciar(View view) {
            String usuario = t_usuario.getText().toString();
            String pass = t_pass.getText().toString();

            if (usuario.isEmpty()){
                t_usuario.setError("Campo Vacio");
                t_usuario.requestFocus();

            }else if(pass.isEmpty()){
                    t_pass.setError("Campo Vacio");
                t_pass.requestFocus();
            }else {



                    //recorre la lista de trasporte buscando si el trasporte esta registrado
                for (Trasporte t: trasporteList) {
                    if (usuario.equals(t.getUsuario()) && pass.equals(t.getContraseña())){
                        Intent intent = new Intent(login.this , MainActivity.class);

                        //enviar los datos al mainActivity
                        intent.putExtra("idTrasporte",t.getIdTrasporte());
                        intent.putExtra("calificacion",t.getCalificacion());
                        intent.putExtra("idLinea",t.getIdLinea());
                        intent.putExtra("nombreConductor",t.getNombreConductor());
                        intent.putExtra("patente",t.getPatente());
                        intent.putExtra("estado",t.isEstado());
                        startActivity(intent);
                        Toast.makeText(this, "sesion iniciada!", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                        return;
                    }
                }

                Toast.makeText(this, "usuario o contraseña incorrecto!", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
    }


    public void limpiarCampos(){
        t_pass.setText("");

    }


    public void crearLista(){
        mydatabasereference.child("trasporte").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                trasporteList.clear();

                //recorre la lista de los trasportes guardados en firebase
                for (DataSnapshot obj : dataSnapshot.getChildren()) {

                 //   Log.e("obj  :",obj.toString());
                    // tranforma el json trasporte de firebase en el objeto trasporte
                    Trasporte t = obj.getValue(Trasporte.class);
                    trasporteList.add(t);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }






}
