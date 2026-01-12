package com.example.bupacas.Datos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

public class DatosProv extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita, folder, basura, soporte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_prov);
        atras=findViewById(R.id.atras);
        atras.setOnClickListener(this);
        casita=findViewById(R.id.casita);
        folder=findViewById(R.id.carpeta);
        basura=findViewById(R.id.basura);
        soporte=findViewById(R.id.soporte);

        casita.setOnClickListener(this);
        folder.setOnClickListener(this);
        basura.setOnClickListener(this);
        soporte.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id==atras.getId())
        {
            finish();
        }
        else if(id==casita.getId())
        {
            startActivity(new Intent(this, Principal.class));
        }
        else if(id==folder.getId())
        {
            startActivity(new Intent(this, NoDisponible.class));
        }
        else if(id==basura.getId())
        {
            startActivity(new Intent(this, NoDisponible.class));
        }
        else if(id==soporte.getId())
        {
            startActivity(new Intent(this, Soporte.class));
        }
    }
}