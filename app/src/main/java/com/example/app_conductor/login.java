package com.example.app_conductor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {


    private EditText t_usuario, t_pass;
    private Button button_login;

    private FirebaseAuth mAuth;
    private FirebaseFirestore myColeccion = FirebaseFirestore.getInstance();

    //declarar circulo de cargando
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        t_usuario = findViewById(R.id.txt_usuario);
        t_pass = findViewById(R.id.txt_pass);
        button_login = findViewById(R.id.btn_ingresar);

        //iniciar progressDialig
        progressDialog = new ProgressDialog(this);
    }

    public void iniciar(View view) {
        String email = t_usuario.getText().toString();
        String pass = t_pass.getText().toString();

        //validar campos
        if (email.isEmpty()) {
            t_usuario.setError("Campo Vacio");
            t_usuario.requestFocus();

        } else if (pass.isEmpty()) {
            t_pass.setError("Campo Vacio");
            t_pass.requestFocus();
        } else {
            LoginUser(email, pass);
        }
    }


    private void LoginUser(String email, String pass) {
        progressDialog.setMessage("verificando datos...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth = FirebaseAuth.getInstance();
                    ValidarTransporte(mAuth.getUid());
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "email o contraseña incorrecto!", Toast.LENGTH_SHORT).show();
                    t_pass.setText("");
                }

            }
        });


    }

    private void ValidarTransporte(String idTransporte) {

        myColeccion.collection("Transporte")
                .document(idTransporte)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Log.e("error : ", document+"");
                            if (document.exists()) {
                                Toast.makeText(getApplicationContext(), "sesion iniciada!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(login.this, MainActivity.class));
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "este usuario no es un conductor inscrito!", Toast.LENGTH_SHORT).show();
                                t_pass.setText("");
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "este usuario no es un conductor inscrito!", Toast.LENGTH_SHORT).show();
                            t_pass.setText("");
                        }
                    }
                });
    }

}
