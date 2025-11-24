package com.example.bupacas;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InicioSesionRegistro extends AppCompatActivity implements View.OnClickListener {

    FrameLayout logindatos, registrodatos;
    LinearLayout contraseñadatos, codigodatos;
    Button registrob, loginsend, loginb, registrosend;
    EditText gmaillogin, contraseñalogin,usuario, gmailregistro, contraseñaregistro, confircontraseña;
    TextView olvidastecontraseñalogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_sesion_registro);

        logindatos=findViewById(R.id.logindatos);
        registrodatos=findViewById(R.id.registrodatos);
        registrob=findViewById(R.id.registrob);
        loginb=findViewById(R.id.iniciosesionb);
        loginsend=findViewById(R.id.iniciosesionsend);
        registrosend=findViewById(R.id.registrosend);
        gmaillogin=findViewById(R.id.gmail_login);
        contraseñalogin=findViewById(R.id.contraseña_login);
        usuario=findViewById(R.id.usuario);
        gmailregistro=findViewById(R.id.gmail_registro);
        contraseñaregistro=findViewById(R.id.contraseña_registro);
        confircontraseña=findViewById(R.id.confirmarcontraseña_registro);
        String tipo=getIntent().getStringExtra("tipo");
        olvidastecontraseñalogin=findViewById(R.id.olvidastecontralogin);
        codigodatos=findViewById(R.id.codigodatos);
        contraseñadatos=findViewById(R.id.contraseñadatos);

        registrob.setOnClickListener(this);
        loginsend.setOnClickListener(this);
        registrosend.setOnClickListener(this);
        loginb.setOnClickListener(this);
        olvidastecontraseñalogin.setOnClickListener(this);

        if(tipo!=null)
        {
            if(tipo.equals("login"))
            {
                logindatos.setVisibility(VISIBLE);
            } else if (tipo.equals("registro"))
            {
                registrodatos.setVisibility(VISIBLE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        String cadenita=olvidastecontraseñalogin.getText().toString();
        if(registrob.getId() == id)
        {
            logindatos.setVisibility(INVISIBLE);
            registrodatos.setVisibility(VISIBLE);
        }
        else if(loginb.getId()==id)
        {
            logindatos.setVisibility(VISIBLE);
            registrodatos.setVisibility(INVISIBLE);
        }
        else if(loginsend.getId()== id)
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
        }
        else if (registrosend.getId()==id)
        {
                Intent intent= new Intent(this, Principal.class);
                startActivity(intent);
        }
        else if (cadenita.equals("¿Olvidaste la contraseña?"))
        {
            codigodatos.setVisibility(VISIBLE);
            contraseñadatos.setVisibility(INVISIBLE);
            olvidastecontraseñalogin.setText("Cliquea si recordaste la contraseña");
        }
        else if(cadenita.equals("Cliquea si recordaste la contraseña"))
        {
            codigodatos.setVisibility(INVISIBLE);
            contraseñadatos.setVisibility(VISIBLE);
            olvidastecontraseñalogin.setText("¿Olvidaste la contraseña?");
        }
    }
}