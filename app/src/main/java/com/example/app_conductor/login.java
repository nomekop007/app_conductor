package com.example.app_conductor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

           crearLista();

                for (Trasporte t: trasporteList) {
                    if (usuario.equals(t.getUsuario()) && pass.equals(t.getContraseña())){
                        Intent intent = new Intent(login.this , MainActivity.class);
                        //enviar los datos al activity usuario
                        intent.putExtra("idTrasporte",t.getIdTrasporte());
                        startActivity(intent);
                    }
                }
            }
    }


    public void crearLista(){
        mydatabasereference.child("trasporte").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                trasporteList.clear();
                for (DataSnapshot obj : dataSnapshot.getChildren()) {

                    Log.e("obj  :",obj.toString());
                    Trasporte t = obj.getValue(Trasporte.class);
                    trasporteList.add(t);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }




}
