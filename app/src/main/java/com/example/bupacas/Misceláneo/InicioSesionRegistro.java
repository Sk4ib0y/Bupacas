package com.example.bupacas.Misceláneo;

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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.DAO.UsuarioDAO;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

public class InicioSesionRegistro extends AppCompatActivity implements View.OnClickListener {

    FrameLayout logindatos, registrodatos;
    LinearLayout contraseñadatos;
    Button registrob, loginsend, loginb, registrosend;
    EditText usuariologin, contraseñalogin,usuario, gmailregistro, contraseñaregistro, confircontraseña;
    UsuarioDAO usuarioDAO;
    SesionManager sesionManager;

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
        usuariologin=findViewById(R.id.usuario_login);
        contraseñalogin=findViewById(R.id.contraseña_login);
        usuario=findViewById(R.id.usuario);
        gmailregistro=findViewById(R.id.gmail_registro);
        contraseñaregistro=findViewById(R.id.contraseña_registro);
        confircontraseña=findViewById(R.id.confirmarcontraseña_registro);

        sesionManager=new SesionManager(this);

        if(sesionManager.isAdmin())
        {
            startActivity(new Intent(this, Principal.class));
            finish();
        }

        String tipo=getIntent().getStringExtra("tipo");
        contraseñadatos=findViewById(R.id.contraseñadatos);

        registrob.setOnClickListener(this);
        loginsend.setOnClickListener(this);
        registrosend.setOnClickListener(this);
        loginb.setOnClickListener(this);
        usuarioDAO=new UsuarioDAO(this);

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
        else if (registrosend.getId()==id)
        {
            String nom=usuario.getText().toString().trim();
            String emailStr=gmailregistro.getText().toString().trim();
            String contraseñaStr=contraseñaregistro.getText().toString().trim();
            String confContraseña=confircontraseña.getText().toString().trim();
            
            if(nom.isEmpty() || emailStr.isEmpty() || contraseñaStr.isEmpty() || confContraseña.isEmpty() )
            {
                Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!confContraseña.equals(contraseñaStr))
            {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            long resultado=usuarioDAO.insertarUsuario(nom, emailStr, contraseñaStr);

            if(resultado!=-1)
            {
                Toast.makeText(this, "¡Usuario registrado correctamente!", Toast.LENGTH_SHORT).show();
                sesionManager.login(nom);
                startActivity(new Intent(this, Principal.class));

            }
            else
            {
                Toast.makeText(this, "Usuario existente", Toast.LENGTH_SHORT).show();
            }
        }

        else if(loginsend.getId()== id)
        {
            String user=usuariologin.getText().toString().trim();
            String pass=contraseñalogin.getText().toString().trim();

            if(user.isEmpty() || pass.isEmpty())
            {
                Toast.makeText(this, "Los campos deben rellenarse", Toast.LENGTH_SHORT).show();
                return;
            }
            if(usuarioDAO.login(user, pass))
            {
                sesionManager.login(user);
                startActivity(new Intent(this, Principal.class));
                Toast.makeText(this, "LogIn exitoso", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}