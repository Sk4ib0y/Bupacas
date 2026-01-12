package com.example.bupacas.Datos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Principal;
import com.example.bupacas.R;
import com.example.bupacas.Misceláneo.Soporte;

public class DatosEnvio extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita, comentarios, basura, carpeta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_envio);

        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        comentarios=findViewById(R.id.soporte);
        carpeta=findViewById(R.id.carpeta);
        basura=findViewById(R.id.basura);

        basura.setOnClickListener(this);
        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        comentarios.setOnClickListener(this);
        carpeta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if(id==atras.getId())
        {
            finish();
        }
        else if(id==casita.getId())
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
        }
        else if(id== comentarios.getId())
        {
            Intent intent= new Intent(this, Soporte.class);
            startActivity(intent);
        }
        else if(id == carpeta.getId())
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if(id==basura.getId())
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
    }
}